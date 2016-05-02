/**
 * Created by Mengxiao Lin on 2016/4/16.
 */

abstract class Element (val str: String){
    abstract fun tex() :String
    abstract val depth :Int
}

class ParsingError: Exception();
class Letter(str:String) : Element(str){
    init{
        if (!str.matches(Regex("[A-Z](_\\{([0-9a-z])*\\})?"))) throw ParsingError()
    }

    override fun tex(): String {
        return str
    }

    override val depth: Int
        get() = 1
}

class Proposition(str: String) : Element(str){

    var connective: String = "\\error"
        get
    var leftChild: Element? = null
    var rightChild: Element? = null

    init {
        var depth = 0
        var connectivePos = -1
        for (i in str.indices){
            if (str[i]=='(') depth++
            if (str[i]==')') depth--
            if (str[i]=='\\' && depth == 1){
                if (connectivePos == -1){
                    connectivePos = i
                }else{
                    throw ParsingError()
                }
            }
        }
        if (connectivePos == -1 || depth !=0) throw ParsingError()
        var rightPart = str.substring(connectivePos)
        if (rightPart.startsWith("\\not")) {
            connective = "\\not"
            rightChild = parse(rightPart.substring(4, rightPart.length-1))
        } else {
            leftChild = parse(str.substring(1, connectivePos))
            if (rightPart.startsWith("\\and")) connective = "\\and"
            if (rightPart.startsWith("\\or")) connective = "\\or"
            if (rightPart.startsWith("\\eq")) connective = "\\eq"
            if (rightPart.startsWith("\\imply")) connective = "\\imply"
            rightChild = parse(rightPart.substring(connective.length, rightPart.length-1))
        }
    }

    override fun tex(): String {
        var connective2operator = {str:String ->
            val m = hashMapOf(
                    "\\and" to " \\wedge ",
                    "\\or" to " \\vee ",
                    "\\not" to " \\neg ",
                    "\\imply" to " \\rightarrow ",
                    "\\eq" to " \\leftrightarrow "
            )
            m[str]
        }

        var str = "("
        if (leftChild!= null) str += leftChild?.tex()
        str += connective2operator(connective)
        str += rightChild?.tex()
        str+=")"
        return str
    }

    override val depth: Int
        get() = let {
            var ret = 0
            if (leftChild !=null) ret = leftChild!!.depth
            if (rightChild!=null) {
                if (rightChild!!.depth>ret) ret = rightChild!!.depth
            }
            return ret+1
        }
}

fun isInParentheses(str: String) :Boolean {
    return str.startsWith("(") && str.endsWith(")");
}

fun parse(str:String) : Element{
    var buf = str.trim().filter { e-> !e.equals(' ') }
    return if (isInParentheses(buf)) Proposition(buf) else Letter(buf)
}
