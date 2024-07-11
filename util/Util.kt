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
}
