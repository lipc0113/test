package com.lpc.test.kotlin_lib.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lpc.test.kotlin_lib.R
import com.lpc.test.kotlin_lib.test.bean.ListNode
import com.lpc.test.kotlin_lib.test.utils.LogUtil

/**
 * @ Author     ：v_lipengcheng
 * @ Date       ：Created in 19:43 2020/10/19
 * @ Description：数据结构与算法
 */
class LeetCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leetcode)
        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData() {

    }

    fun test(view: View) {
        val nums = intArrayOf(0, 2, 7, 11, 15)
        val target = 9
        val test = twoSum(nums, target)
        LogUtil.d("twoSum(nums, target) = ${test[0]},${test[1]}")
    }

    /**
     * 两数之和
     *
     * nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     * */
    fun twoSum(nums: IntArray, target: Int): IntArray {

        val mutableMapOf = mutableMapOf<Int, Int>()
        for ((index, element) in nums.withIndex()) {
            if (mutableMapOf.containsKey(target - element)) {
                return intArrayOf(mutableMapOf[target - element]!!, index)
            } else {
                mutableMapOf[element] = index
            }
        }
        return intArrayOf(0, 0)
    }

    fun test2(view: View) {
        val node = ListNode(2)
        val node2 = ListNode(4)
        val node3 = ListNode(3)
        node.next = node2
        node2.next = node3
        val node4 = ListNode(5)
        val node5 = ListNode(6)
        val node6 = ListNode(4)
        node4.next = node5
        node5.next = node6
        val test2 = addTwoNumbers(node, node4)
        LogUtil.d("addTwoNumbers(node, node4) = ${test2?.value}")
        LogUtil.d("addTwoNumbers(node, node4) = ${test2?.next?.value}")
        LogUtil.d("addTwoNumbers(node, node4) = ${test2?.next?.next?.value}")
    }

    /**
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     * */
    fun addTwoNumbers(l11: ListNode?, l22: ListNode?): ListNode? {

        var l1 = l11
        var l2 = l22
        var head: ListNode? = null
        var tail: ListNode? = null
        var carry: Int = 0
        var listNode: ListNode? = null
        while (l1 != null || l2 != null) {
            var value: Int = ((l1?.value ?: 0) + (l2?.value ?: 0) + carry) % 10
            listNode = ListNode(value)
            if (head == null) {
                head = listNode
                tail = listNode
            } else {
                tail!!.next = listNode
                tail = tail.next
            }
            carry = ((l1?.value ?: 0) + (l2?.value ?: 0) + carry) / 10
            l1 = l1?.next
            l2 = l2?.next
        }
        if (carry != 0) {
            listNode = ListNode(carry)
            tail!!.next = listNode
        }
        return head
    }

    fun test3(view: View) {
        LogUtil.d("lengthOfLongestSubstring(\"abcabcbb\") = ${lengthOfLongestSubstring("abcabcbb")}")
    }

    /**
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    fun lengthOfLongestSubstring(s: String): Int {
        var maxLength = 0
        val hashSetOf = hashSetOf<Char>()
        var right = 0
        val length = s.length
        val toCharArray = s.toCharArray()
        for ((index, value) in toCharArray.withIndex()) {
            if (index != 0) {
                hashSetOf.remove(s.get(index - 1))
            }
            while (right < length && !hashSetOf.contains(s.get(right))) {
                hashSetOf.add(s.get(right))
                right++
            }
            maxLength = Math.max(maxLength, right - index)
        }

        return maxLength
    }

    fun test5(view: View) {
        val longestPalindrome = longestPalindrome("babad")
        LogUtil.d("longestPalindrome = ${longestPalindrome}")
    }

    /**
     * 最长回文子串
     * */
    fun longestPalindrome(s: String): String {

        var ans = ""
        var len = s.length
        val booleanArray = Array(len) { Array(len) { false } }
        val charArray = s.toCharArray()
        for (l in 0 until len) {
            for (i in 0 until len - l) {
                var j = i + l
                when (l) {
                    0 -> booleanArray[i][j] = true
                    1 -> booleanArray[i][j] = (charArray[i] == charArray[j])
                    else -> booleanArray[i][j] = (booleanArray[i + 1][j - 1] && charArray[i] == charArray[j])
                }
                if (booleanArray[i][j] && (l + 1 > ans.length)) {
                    ans = s.substring(i, i + l + 1)
                }
            }

        }
        return ans
    }

    fun test6(view: View) {
        val convert = convert("LEETCODEISHIRING", 3)
        LogUtil.d("convert = ${convert}")
    }

    /**
     * Z字形变换
     *
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     *
     * 输入字符串为 "LEETCODEISHIRING" 行数为3时,输出"LCIRETOESIIGEDHN"
     * */
    fun convert(s: String, numRows: Int): String {

        if (numRows == 1) {
            return s
        }

        if (s.length <= numRows) {
            return s
        }

        var list = arrayListOf<StringBuilder>()
        for (i in 0 until numRows) {
            list.add(StringBuilder())
        }

        var line = 0
        val length = s.length
        var b = false
        for (i in 0 until length) {

            list.get(line).append(s.get(i))

            if (line == numRows - 1 || line == 0) b = !b

            if (b) {
                line++
            } else {
                line--
            }
        }

        val sb = StringBuilder()
        for (i in 0 until numRows) {
            sb.append(list.get(i).toString())
        }

        return sb.toString()
    }

    fun test7(view: View) {
        println("reverse(1452) = ${reverse(1452)}")
    }

    /**
     * 整数反转
     *
     * int类型的范围是 -2^31——2^31-1，即-2147483648——2147483647
     * */
    fun reverse(x: Int): Int {

        var resource = x
        var dest = 0

        while (resource != 0) {
            val temp = resource % 10
            resource /= 10

            if (dest > Int.MAX_VALUE / 10 || dest == Int.MAX_VALUE / 10 && temp > Int.MAX_VALUE % 10) {
                return 0
            }
            if (dest < Int.MIN_VALUE / 10 || dest == Int.MIN_VALUE / 10 && temp < Int.MIN_VALUE % 10) {
                return 0
            }
            dest = dest * 10 + temp
        }
        return dest
    }

    fun test8(view: View) {
        println("myAtoi(   -42) = ${myAtoi("   -42")}")
        println("myAtoi(-a42) = ${myAtoi("-a42")}")
        println("myAtoi(42) = ${myAtoi("42")}")
        println("myAtoi(a42) = ${myAtoi("a42")}")
        println("myAtoi(aaaa) = ${myAtoi("aaaa")}")
        println("myAtoi(--42) = ${myAtoi("--42")}")
        println("myAtoi(+42) = ${myAtoi("+42")}")
        println("myAtoi(21474836460) = ${myAtoi("21474836460")}")
    }

    fun myAtoi(s: String): Int {
        val hashMapOf = hashMapOf<String, Array<String>>(
                Pair("start", arrayOf("start", "sign", "number", "end")),
                Pair("sign", arrayOf("end", "end", "number", "end")),
                Pair("number", arrayOf("end", "end", "number", "end")),
                Pair("end", arrayOf("end", "end", "end", "end"))
        )
        var currentState = "start"
        var value = 0
        var sign = 1
        var temp = 0

        for (index in s.indices) {
            currentState = hashMapOf.get(currentState)!!.get(getType(s.get(index)))

            when (currentState) {
                "start" -> currentState = "start"
                "sign" -> {
                    if (s.get(index).toString() == "+") sign = 1 else sign = -1
                }
                "number" -> {
                    temp = s.get(index).toString().toInt()

                    if (value * sign < Int.MIN_VALUE / 10 || value * sign == Int.MIN_VALUE / 10 && temp * sign <= Int.MIN_VALUE % 10) {
                        currentState = "end"
                        value = Int.MIN_VALUE
                        sign = 1
                    } else if (value > Int.MAX_VALUE / 10 || value == Int.MAX_VALUE / 10 && temp > Int.MAX_VALUE % 10) {
                        currentState = "end"
                        value = Int.MAX_VALUE
                    } else {
                        value = value * 10 + temp
                    }
                }
                else -> {
                    currentState = "end"
                }
            }

            if (currentState == "end") break;

        }

        return sign * value
    }

    fun getType(first: Char): Int {
        var type = 0
        when {
            first.isWhitespace() -> type = 0
            first.toString() == "+" || first.toString() == "-" -> {
                type = 1
            }
            first.isDigit() -> {
                type = 2
            }
            else -> type = 3
        }
        return type
    }
}