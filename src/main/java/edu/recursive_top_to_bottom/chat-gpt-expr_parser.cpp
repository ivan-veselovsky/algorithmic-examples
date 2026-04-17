// expr_parser.cpp
#include <cctype>
#include <cstdlib>
#include <iostream>
#include <stdexcept>
#include <string>
#include <optional>

struct Token {
    enum Kind { End, Number, Plus, Minus, Mul, Div, LParen, RParen } kind;
    double value = 0.0; // valid when kind == Number
};

class Lexer {
public:
    explicit Lexer(const std::string& src) : s(src), i(0) {}

    Token next() {
        skipSpaces();
        if (i >= s.size()) return {Token::End};

        char c = s[i];

        // Numbers (supports: 123, 123.45, .45, 1e3, 1.2e-3)
        if (std::isdigit(c) || c == '.') {
            size_t start = i;
            bool seenDot = (c == '.');
            ++i;
            while (i < s.size() && (std::isdigit(s[i]) || s[i] == '.')) {
                if (s[i] == '.') {
                    if (seenDot) break;
                    seenDot = true;
                }
                ++i;
            }
            // Optional exponent
            if (i < s.size() && (s[i] == 'e' || s[i] == 'E')) {
                size_t epos = i++;
                if (i < s.size() && (s[i] == '+' || s[i] == '-')) ++i;
                size_t digits = i;
                while (i < s.size() && std::isdigit(s[i])) ++i;
                if (digits == i) i = epos; // back off if exponent malformed
            }
            double v = std::strtod(s.c_str() + start, nullptr);
            return Token{Token::Number, v};
        }

        ++i;
        switch (c) {
            case '+': return {Token::Plus};
            case '-': return {Token::Minus};
            case '*': return {Token::Mul};
            case '/': return {Token::Div};
            case '(': return {Token::LParen};
            case ')': return {Token::RParen};
            default:
                throw std::runtime_error(std::string("Unexpected character: ") + c);
        }
    }

    // Peek without consuming
    const Token& peek() {
        if (!lookahead) lookahead = next();
        return *lookahead;
    }

    // Consume and return next token
    Token eat() {
        if (lookahead) {
            Token t = *lookahead;
            lookahead.reset();
            return t;
        }
        return next();
    }

private:
    void skipSpaces() {
        while (i < s.size() && std::isspace(static_cast<unsigned char>(s[i]))) ++i;
    }

    const std::string& s;
    size_t i;
    std::optional<Token> lookahead;
};

class Parser {
public:
    explicit Parser(Lexer& lex) : L(lex) {}

    // expression := term { ('+'|'-') term }
    double parseExpression() {
        double left = parseTerm();
        for (;;) {
            auto k = L.peek().kind;
            if (k == Token::Plus) {
                L.eat();
                left += parseTerm();
            } else if (k == Token::Minus) {
                L.eat();
                left -= parseTerm();
            } else break;
        }
        return left;
    }

private:
    // term := primary { ('*'|'/') primary }
    double parseTerm() {
        double left = parsePrimary();
        for (;;) {
            auto k = L.peek().kind;
            if (k == Token::Mul) {
                L.eat();
                left *= parsePrimary();
            } else if (k == Token::Div) {
                L.eat();
                double rhs = parsePrimary();
                if (rhs == 0.0) throw std::runtime_error("Division by zero");
                left /= rhs;
            } else break;
        }
        return left;
    }

    // primary := number | '(' expression ')' | unary
    // unary := ('+'|'-') primary
    double parsePrimary() {
        Token t = L.peek();
        if (t.kind == Token::Plus) { L.eat(); return +parsePrimary(); }
        if (t.kind == Token::Minus) { L.eat(); return -parsePrimary(); }

        t = L.eat();
        switch (t.kind) {
            case Token::Number: return t.value;
            case Token::LParen: {
                double v = parseExpression();
                if (L.eat().kind != Token::RParen)
                    throw std::runtime_error("Expected ')'");
                return v;
            }
            default:
                throw std::runtime_error("Expected a number or '('");
        }
    }

    Lexer& L;
};

int main() {
    std::cout.setf(std::ios::fmtflags(0), std::ios::floatfield);
    std::cout.precision(15);

    std::string line;
    while (std::cout << "> " && std::getline(std::cin, line)) {
        try {
            Lexer lex(line);
            Parser p(lex);
            double result = p.parseExpression();
            // Ensure full consumption (optional)
            if (lex.peek().kind != Token::End)
                throw std::runtime_error("Trailing input");
            std::cout << result << "\n";
        } catch (const std::exception& e) {
            std::cerr << "error: " << e.what() << "\n";
        }
    }
    return 0;
}
