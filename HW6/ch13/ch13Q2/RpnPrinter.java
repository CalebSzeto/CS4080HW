package com.craftinginterpreters.lox;

class RpnPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
    }

    @Override
    public String visitTernaryExpr(Expr.Ternary expr) {
        return expr.condition.accept(this) + " " + expr.thenBranch.accept(this) + " " + expr.elseBranch.accept(this) + " ?:";
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return expr.expression.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return expr.right.accept(this) + " " + expr.operator.lexeme;
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return expr.value.accept(this) + " " + expr.name.lexeme + " =";
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        StringBuilder result = new StringBuilder();
        
        for (Expr argument : expr.arguments) {
            result.append(argument.accept(this)).append(" ");
        }
        result.append(expr.callee.accept(this)).append(" call");
        
        return result.toString();
    }

    @Override
    public String visitGetExpr(Expr.Get expr) {
        return expr.object.accept(this) + " " + expr.name.lexeme + " .";
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return expr.object.accept(this) + " " + expr.value.accept(this) + " " + expr.name.lexeme + " =";
    }

    @Override
    public String visitInnerExpr(Expr.Inner expr) {
        return "inner " + expr.method.lexeme + " .";
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return "this";
    }

    @Override
    public String visitFunctionExpr(Expr.Function expr) {
        return "fun";  
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
    }
}