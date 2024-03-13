package ru.vk.kotlin_advanced


class TreeNode(val parent: TreeNode?)

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {

        when {
            (T::class is Number) -> Unit
        }

        p = p.parent
    }
    return p as T?
}