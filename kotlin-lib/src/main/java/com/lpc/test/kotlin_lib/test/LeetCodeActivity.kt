package com.lpc.test.kotlin_lib.test

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lpc.test.kotlin_lib.R
import com.lpc.test.kotlin_lib.test.bean.ListNode
import com.lpc.test.kotlin_lib.test.bean.TreeNode
import com.lpc.test.kotlin_lib.test.utils.LogUtil
import java.util.*

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
     * 利用taget-index上的值，来求解
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
     * 考虑进位的问题
     *
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
     * 滑动窗口
     *
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
     * 动态规划
     *
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

    /**
     * 自动机（有限状态机）
     *
     * 注意：1.Int的取值范围；2.value做判断的时候，小心越界
     * */
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

    fun test9(view: View) {
        println("isPalindrome(-1) = ${isPalindrome(-1)}")
        println("isPalindrome(12321) = ${isPalindrome(12321)}")
        println("isPalindrome(-12321) = ${isPalindrome(-12321)}")
        println("isPalindrome(112321) = ${isPalindrome(112321)}")
        println("isPalindrome(998899) = ${isPalindrome(998899)}")
    }

    /**
     * 使用移动一半数字的算法更佳
     * */
    fun isPalindrome(x: Int): Boolean {
        var value = x
        val arrayListOf = arrayListOf<Int>()
        if (x < 0) {
            return false
        }
        if (x == 0) {
            return true
        }
        while (value != 0) {
            arrayListOf.add(value % 10)
            value = value / 10
        }
        val stringBuilder = StringBuilder()
        for (i in 0 until arrayListOf.size) {
            stringBuilder.append(arrayListOf.get(i))
        }
        return stringBuilder.toString() == x.toString()
    }

    fun test11(view: View) {
        println("maxArea(intArrayOf(1,8,6,2,5,4,8,3,7)) = ${maxArea(intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7))}")
    }

    /**
     * 双指针
     * */
    fun maxArea(height: IntArray): Int {
        var max = 0
        var left = 0
        var right = height.size - 1
        while (left < right) {
            if (height[left] <= height[right]) {
                max = Math.max(height[left] * (right - left), max)
                left++
            } else {
                max = Math.max(height[right] * (right - left), max)
                right--
            }
        }

        return max
    }

    fun test12(view: View) {
        LogUtil.d("intToRoman(1994) = ${intToRoman(1994)}")
        LogUtil.d("intToRoman(1834) = ${intToRoman(1834)}")
    }

    /**
     * 方法一、贪心算法
     *
     * 方法二、暴力求解（利用数组把数字穷举会比较简单）
     * */
    fun intToRoman(num: Int): String {
        var num = num
        val numbers = arrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        val romans = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
        val stringBuilder = StringBuilder()
        var index = 0
        while (num > 0) {
            while (num >= numbers[index]) {
                num = num - numbers[index]
                stringBuilder.append(romans[index])
            }
            index++
        }
        return stringBuilder.toString()
    }

    fun test13(view: View) {
        LogUtil.d("romanToInt(MCMXCIV) = ${romanToInt("MCMXCIV")}")
    }

    fun romanToInt(s: String): Int {
        var s = s
        val numbers = arrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        val romans = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
        var index = 0
        var value = 0
        while (s.length > 0) {
            while (s.startsWith(romans[index])) {
                s = s.substring(romans[index].length)
                value += numbers[index]
            }
            index++
        }
        return value
    }

    fun test14(view: View) {

        LogUtil.d("longestCommonPrefix(arrayOf(\"flower\",\"flow\",\"flight\")) " +
                "= ${longestCommonPrefix(arrayOf("flower", "flow", "flight"))}")
    }

    /**
     * 纵向扫描
     * */
    fun longestCommonPrefix(strs: Array<String>): String {

        val first = strs[0]
        val length = first.length
        for (i in 0 until length) {

            for (j in 1 until strs.size) {
                if (strs[j] == "") {
                    return ""
                }
                if (strs[j].length == i || strs[j][i] != (first[i])) {
                    if (i == 0) {
                        return ""
                    }
                    return first.substring(0, i)
                }
            }
        }
        return first
    }

    fun test14_2(view: View) {
        LogUtil.d("longestCommonPrefix_2(arrayOf(\"flower\",\"flow\",\"flight\")) " +
                "= ${longestCommonPrefix_2(arrayOf("flower", "flow", "flight"))}")
        LogUtil.d("longestCommonPrefix_2(arrayOf(\"flight\",\"flow\",\"flower\")) " +
                "= ${longestCommonPrefix_2(arrayOf("flower", "flow", "flight"))}")
        LogUtil.d("longestCommonPrefix_2(arrayOf(\"dog\",\"racecar\",\"car\")) " +
                "= ${longestCommonPrefix_2(arrayOf("dog", "racecar", "car"))}")
    }

    /**
     * 字典树
     * */
    fun longestCommonPrefix_2(strs: Array<String>): String {

        val size = strs.size
        var treeNode = TreeNode('s'.toChar())

        for (i in 0 until size) {
            addNode(strs[i], treeNode)
        }
        val sb = StringBuilder()
        while (treeNode.children.size == 1 && !treeNode.isEnd) {
            sb.append(treeNode.children.keys.iterator().next())
            treeNode = treeNode.children[treeNode.children.keys.iterator().next()]!!
        }
        return sb.toString()
    }

    private fun addNode(s: String, root: TreeNode) {

        var root = root
        val length = s.length
        for (i in 0 until length) {

            val char = s[i]
            val node = TreeNode(char)
            if (!root.children.containsKey(node.value)) {
                root.children.put(node.value, node)
            }
            root = root.children[node.value]!!
        }
        root.isEnd = true
    }

    fun test15(view: View) {
        var nums = intArrayOf(-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6)
        LogUtil.d("threeSum(nums) = ${threeSum(nums)}")
    }

    fun threeSum(nums: IntArray): List<List<Int>> {
        var list: MutableList<Int>? = null
        val listOf = mutableListOf<List<Int>>()

        nums.sort()
        for (i in nums.indices) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue
            }
            var left = i
            var right = nums.size - 1
            while (left < right) {
                left++
                while (left < right && nums[left] == nums[left - 1] && left - 1 > i) {
                    left++
                }
                while (left < right && nums[left] + nums[right] < -nums[i]) {
                    left++
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++
                    }
                }
                while (left < right && nums[left] + nums[right] > -nums[i]) {
                    right--
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--
                    }
                }
                if (nums[left] + nums[right] == -nums[i] && left < right) {
                    list = mutableListOf()
                    list.add(nums[i])
                    list.add(nums[left])
                    list.add(nums[right])
                    listOf.add(list)
                }
            }

        }

        return listOf
    }

    fun test16(view: View) {
        var nums = intArrayOf(-1, 0, 1, 1, 55)
        LogUtil.d("threeSumClosest(nums, target) = ${threeSumClosest(nums, 3)}")
    }

    fun threeSumClosest(nums: IntArray, target: Int): Int {

        nums.sort()
        var min = nums[0] + nums[1] + nums[2]
        for (i in 0 until nums.size - 2) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue
            }

            var left = i + 1
            var right = nums.size - 1
            while (left < right) {
                var sum = nums[left] + nums[right] + nums[i]
                if (sum == target) {
                    return target
                }
                if (sum < target) {
                    left++
                    while (left > i + 1 && left < right && nums[left] == nums[left - 1]) {
                        left++
                    }
                }

                if (sum > target) {
                    right--
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--
                    }
                }

                if (Math.abs(sum - target) < Math.abs(min - target)) {
                    min = sum
                }
            }
        }

        return min
    }

    fun test20(view: View) {
        println("isValid({[]}) = ${isValid("{[]}")}")
    }

    fun isValid(s: String): Boolean {

        val preArray = arrayOf('(', '[', '{')
        val backArray = arrayOf(')', ']', '}')
        val map = mapOf<Char, Char>('(' to ')', '[' to ']', '{' to '}')

        val arrayDeque = ArrayDeque<Char>(s.length)
        for (i in s.indices) {
            val c = s[i]
            if (preArray.contains(c)) {
                arrayDeque.push(c)
            } else if (backArray.contains(c) && !arrayDeque.isEmpty()) {
                val poll = arrayDeque.pop()
                if (map[poll] != c) {
                    return false
                }
            } else if (c == ' ') {
                continue
            } else {
                return false
            }
        }

        return arrayDeque.isEmpty()
    }

    fun test21(view: View) {
        val node = ListNode(2)
        val node2 = ListNode(6)
        val node3 = ListNode(7)
        node.next = node2
        node2.next = node3
        val node4 = ListNode(3)
        val node5 = ListNode(4)
        val node6 = ListNode(9)
        node4.next = node5
        node5.next = node6

        var mergeTwoLists = mergeTwoLists(node, node4)
        while (mergeTwoLists != null) {
            LogUtil.d("mergeTwoLists = ${mergeTwoLists.value}")
            mergeTwoLists = mergeTwoLists.next

        }
    }

    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {

        var l1 = l1
        var l2 = l2
        val first: ListNode = ListNode(-1)
        var temp = first
        while (l1 != null && l2 != null) {
            if (l2.value > l1.value) {
                temp.next = l1
                l1 = l1.next
            } else {
                temp.next = l2
                l2 = l2.next
            }
            temp = temp.next!!
        }

        temp.next = (l1 ?: l2)
        return first.next
    }

    fun test21_2(view: View) {
        val node = ListNode(2)
        val node2 = ListNode(6)
        val node3 = ListNode(7)
        node.next = node2
        node2.next = node3
        val node4 = ListNode(3)
        val node5 = ListNode(4)
        val node6 = ListNode(9)
        node4.next = node5
        node5.next = node6

        var mergeTwoLists = mergeTwoLists2(node, node4)
        while (mergeTwoLists != null) {
            LogUtil.d("mergeTwoLists = ${mergeTwoLists.value}")
            mergeTwoLists = mergeTwoLists.next

        }
    }

    fun mergeTwoLists2(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null) {
            return l2
        } else if (l2 == null) {
            return l1
        } else if (l1.value < l2.value) {
            l1.next = mergeTwoLists2(l1.next, l2)
            return l1
        } else {
            l2.next = mergeTwoLists2(l2.next, l1)
            return l2
        }
    }

    fun test22(view: View) {
        LogUtil.d("generateParenthesis() = ${generateParenthesis2(4)}")
    }

    /**
     * 字符串每次都是新生成的，严格来说并不是回溯算法
     * */
    fun generateParenthesis(n: Int): List<String> {
        val mutableListOf = mutableListOf<String>()
        if (n == 0) {
            return mutableListOf
        }
        recur(mutableListOf, "", 0, 0, n)
        return mutableListOf
    }

    fun recur(mutableListOf: MutableList<String>, s: String, left: Int, right: Int, count: Int) {
        if (left == count && right == count) {
            mutableListOf.add(s)
        }

        if (left < count) {
            recur(mutableListOf, "$s(", left + 1, right, count)
        }

        if (right < left) {
            recur(mutableListOf, "$s)", left, right + 1, count)
        }
    }

    /**
     * 回溯算法
     * */
    fun generateParenthesis2(n: Int): List<String> {
        val mutableListOf = mutableListOf<String>()
        if (n == 0) {
            return mutableListOf
        }
        recur2(mutableListOf, StringBuilder(), 0, 0, n)
        return mutableListOf
    }

    fun recur2(mutableListOf: MutableList<String>, sb: StringBuilder, left: Int, right: Int, count: Int) {
        if (left == count && right == count) {
            mutableListOf.add(sb.toString())
        }

        if (left < count) {
            recur2(mutableListOf, sb.append("("), left + 1, right, count)
            sb.deleteCharAt(sb.length - 1)
        }

        if (right < left) {
            recur2(mutableListOf, sb.append(")"), left, right + 1, count)
            sb.deleteCharAt(sb.length - 1)
        }
    }

    /**
     * 最基本的回溯算法
     * */
    fun test46(view: View) {
        val intArrayOf = intArrayOf(1, 2, 3)
        val permute = permute(intArrayOf)
        LogUtil.d("permute = ${permute}")
    }

    fun permute(nums: IntArray): List<List<Int>> {
        val results = mutableListOf<MutableList<Int>>()
        if (nums.isEmpty()) {
            return results
        }
        val list = mutableListOf<Int>()
        for (i in nums.indices) {
            list.add(nums[i])
        }
        val size = list.size
        backtrack(size, list, results, 0)
        return results
    }

    fun backtrack(size: Int, list: MutableList<Int>, results: MutableList<MutableList<Int>>, first: Int) {
        if (first == size) {
            val temp = mutableListOf<Int>()
            temp.addAll(list)
            results.add(temp)
        }
        for (i in first until size) {
            Collections.swap(list, first, i)
            backtrack(size, list, results, first + 1)
            Collections.swap(list, first, i)
        }
    }

    fun test544(view: View) {
        LogUtil.d("findContestMatch(8) = ${findContestMatch(8)}")
    }

    fun findContestMatch(n: Int): String {

        return ""
    }

    fun findContestMatchRecur(stringBuilder: StringBuilder, pre: Int, tail: Int) {


    }

    /**
     * 合并K个升序链表
     * */
    fun test23(view: View) {
        val node = ListNode(2)
        val node2 = ListNode(6)
        val node3 = ListNode(7)
        node.next = node2
        node2.next = node3
        val node4 = ListNode(3)
        val node5 = ListNode(4)
        val node6 = ListNode(9)
        node4.next = node5
        node5.next = node6
        val node7 = ListNode(3)
        val node8 = ListNode(4)
        val node9 = ListNode(9)
        node7.next = node8
        node8.next = node9

        val arrayOf: Array<ListNode?> = arrayOf(node, node4, node7)
        var mergeKLists = mergeKLists(arrayOf)
        while (mergeKLists != null) {
            LogUtil.d("mergeTwoLists = ${mergeKLists.value}")
            mergeKLists = mergeKLists.next

        }
    }

    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) {
            return null
        }
        val right = lists.size - 1
        val left = 0
        return mergeKLists(lists, left, right)
    }

    fun mergeKLists(lists: Array<ListNode?>, left: Int, right: Int): ListNode? {
        if (left == right) {
            return lists[left]
        }
        val mid = (left + right) shr 1

        return mergeTwoLists(mergeKLists(lists, left, mid), mergeKLists(lists, mid + 1, right))
    }

    fun test24(view: View) {
        val node = ListNode(1)
        val node2 = ListNode(2)
        val node3 = ListNode(3)
        val node4 = ListNode(4)
        val node5 = ListNode(5)
        val node6 = ListNode(6)
        node.next = node2
        node2.next = node3
        node3.next = node4
        node4.next = node5
        node5.next = node6

        var mergeKLists = swapPairs(node)
        while (mergeKLists != null) {
            LogUtil.d("mergeTwoLists = ${mergeKLists.value}")
            mergeKLists = mergeKLists.next

        }
    }

    fun swapPairs(head: ListNode?): ListNode? {

        var pre: ListNode? = null
        var current: ListNode? = head
        var next: ListNode? = null
        val dest = current?.next
        if (current == null || dest == null) {
            return current
        }

        var point: ListNode? = null
        while (current?.next != null) {
            pre = current
            current = current.next
            next = current?.next

            point?.next = current
            point = pre
            current?.next = pre

            current = next
            point.next = current
        }

        return dest
    }

    fun test25(view: View) {
        val node = ListNode(1)
        val node2 = ListNode(2)
        val node3 = ListNode(3)
        val node4 = ListNode(4)
        val node5 = ListNode(5)
        val node6 = ListNode(6)
        val node7 = ListNode(7)
        node.next = node2
        node2.next = node3
        node3.next = node4
        node4.next = node5
        node5.next = node6
        node6.next = node7

        var reverseKGroup = reverseKGroup(node, 4)
        while (reverseKGroup != null) {
            LogUtil.d("mergeTwoLists = ${reverseKGroup.value}")
            reverseKGroup = reverseKGroup.next

        }
    }

    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {

        if (k < 2) {
            return head
        }

        var head = head

        var root: ListNode? = ListNode(-1)
        var next: ListNode? = head

        var preTail: ListNode? = root
        var temp: ListNode? = null
        var first: ListNode? = null

        while (next != null) {

            preTail?.next = next
            first = next
            temp = next

            for (i in 2..k) {
                temp = temp?.next
                if (temp == null) {
                    next = temp
                    break
                }
                if (i == k) {
                    preTail?.next = temp
                    preTail = first

                    next = temp?.next
                    temp?.next = null

                    单链表反转(first)
                }
            }
        }

        return root?.next
    }

    fun 单链表反转(node10: ListNode?): ListNode? {
        var pre: ListNode? = null
        var current = node10
        var next: ListNode? = null
        while (current != null) {
            next = current?.next
            current.next = pre
            pre = current
            current = next
        }
        return pre
    }


}