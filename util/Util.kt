package util

import ArrayAccess
import ArrayType
import BinaryOperation
import Block
import ExpressionStatement
import FunctionCall
import FunctionDeclaration
import Identifier
import IfStatement
import Literal
import MemberAccess
import Node
import Program
import SimpleType
import StructDeclaration
import StructInitializer
import VariableDeclaration

@SuppressWarnings("ALL")
class Util {
    open fun padding(str: String, len: Int): String {
        return str + (" ".repeat(len - str.length))
    }

    fun prettyAST(node: Node, indent: String = ""): String {
        return when (node) {
            is Program -> {
                val sb = StringBuilder()
                sb.append("${indent}Program\n")
                node.statements.forEach { sb.append(prettyAST(it, "$indent  ")) }
                sb.toString()
            }
            is VariableDeclaration -> "${indent}VariableDeclaration: ${node.name} (${prettyAST(node.type, "")})\n" +
                    "${prettyAST(node.initializer, "$indent  ")}"
            is FunctionDeclaration -> {
                val sb = StringBuilder()
                sb.append("${indent}FunctionDeclaration: ${node.name}\n")
                sb.append("${indent}  Parameters:\n")
                node.parameters.forEach { sb.append("${indent}    ${it.name}: ${prettyAST(it.type, "")}\n") }
                sb.append("${indent}  ReturnType: ${prettyAST(node.returnType, "")}\n")
                sb.append("${indent}  Body:\n")
                sb.append(prettyAST(node.body, "$indent    "))
                sb.toString()
            }
            is ExpressionStatement -> "${indent}ExpressionStatement:\n${prettyAST(node.expression, "$indent  ")}"
            is IfStatement -> {
                val sb = StringBuilder()
                sb.append("${indent}IfStatement:\n")
                sb.append("${indent}  Condition:\n${prettyAST(node.condition, "$indent    ")}")
                sb.append("${indent}  Then:\n${prettyAST(node.body, "$indent    ")}")
                if (node.elseBody != null) {
                    sb.append("${indent}  Else:\n${prettyAST(node.elseBody, "$indent    ")}")
                }
                sb.toString()
            }
            is Literal -> "${indent}Literal: ${node.value}\n"
            is Identifier -> "${indent}Identifier: ${node.name}\n"
            is BinaryOperation -> "${indent}BinaryOperation: ${node.operator}\n" +
                    "${prettyAST(node.left, "$indent  ")}" +
                    "${prettyAST(node.right, "$indent  ")}"
            is FunctionCall -> {
                val sb = StringBuilder()
                sb.append("${indent}FunctionCall:\n")
                sb.append("${prettyAST(node.function, "$indent  ")}")
                sb.append("${indent}  Arguments:\n")
                node.arguments.forEach { sb.append(prettyAST(it, "$indent    ")) }
                sb.toString()
            }
            is ArrayAccess -> "${indent}ArrayAccess:\n" +
                    "${prettyAST(node.array, "$indent  ")}" +
                    "${indent}  Index:\n${prettyAST(node.index, "$indent    ")}"
            is StructInitializer -> {
                val sb = StringBuilder()
                sb.append("${indent}StructInitializer: ${node.structName}\n")
                node.fields.forEach { (name, value) ->
                    sb.append("${indent}  $name:\n${prettyAST(value, "$indent    ")}")
                }
                sb.toString()
            }
            is MemberAccess -> "${indent}MemberAccess:\n" +
                    "${prettyAST(node.`obj`, "$indent  ")}" +
                    "${indent}  Member: ${node.member}\n"
            is Block -> {
                val sb = StringBuilder()
                sb.append("${indent}Block:\n")
                node.statements.forEach { sb.append(prettyAST(it, "$indent  ")) }
                sb.toString()
            }
            is SimpleType -> "${indent}Type: ${node.name}\n"
            is ArrayType -> "${indent}ArrayType:\n${prettyAST(node.elementType, "$indent  ")}"
            is StructDeclaration -> {
                val sb = StringBuilder()
                sb.append("${indent}StructDeclaration: ${node.name}\n")
                node.fields.forEach { (name, type) ->
                    sb.append("${indent}  $name: ${prettyAST(type, "")}")
                }
                sb.toString()
            }
            else -> "${indent}Unknown node type: ${node::class.simpleName}\n"
        }
    }
}