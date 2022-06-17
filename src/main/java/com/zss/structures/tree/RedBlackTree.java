package com.zss.structures.tree;

import com.zss.structures.exceptions.UnderFlowException;
import com.zss.structures.tree.inter.Tree;

/**
 * @author ZSS
 * @date 2022/5/22 10:02
 * @desc 红黑树: 执行插入所需要的开销相对较低，另外就是实践中发生的旋转相对较少
 * 红黑树构成条件：
 * 1. 每一个节点非黑即红
 * 2. 根是黑色
 * 3. 如果一个节点是红色的，那么它的子节点必须是黑色的 -- 不能有连续的红节点
 * 4. 从一个节点到一个null引用的每一条路劲必须包含相同数目的黑色节点
 */
@SuppressWarnings("unused")
public class RedBlackTree<T extends Comparable<? super T>> implements Tree<T> {

    private final RedBlackNode<T> header;
    private final RedBlackNode<T> nullNode;

    private static final int BLACK = 1;
    private static final int RED = 0;

    /**
     * construct the tree
     */
    RedBlackTree() {
        nullNode = new RedBlackNode<>(null);
        nullNode.left = nullNode.right = nullNode;
        header = new RedBlackNode<>(null);
        header.left = header.right = nullNode;
    }

    @Override
    public void makeEmpty() {
        header.right = nullNode;
    }

    @Override
    public boolean isEmpty() {
        return header.right == nullNode;
    }

    @Override
    public boolean contains(T x) {
        return contains(x, header.right);
    }

    @Override
    public T findMin() throws UnderFlowException {
        if (isEmpty()){
            throw new UnderFlowException("Empty tree!!!");
        }
        return findMin(header.right).element;
    }

    @Override
    public T findMax() throws UnderFlowException {
        if (isEmpty()){
            throw new UnderFlowException("Empty tree!!!");
        }
        return null;
    }

    /**
     * 仅用于插入方法及其助手
     * current: 当前节点
     * parent: 父节点
     * grand: 祖父节点
     * great: 曾祖父节点
     */
    private RedBlackNode<T> current;
    private RedBlackNode<T> parent;
    private RedBlackNode<T> grand;
    private RedBlackNode<T> great;

    @Override
    public void insert(T item) {
        current = parent = grand = header;
        nullNode.element = item;

        // 在所有空节点都为nullNode（且nullNode.ele = item）的前提下
        // 依据左小右大的规则，一路找到对应的nullNode
        while (compare(item, current) != 0) {
            great = grand;
            grand = parent;
            parent = current;

            // 左小右大: left or right
            // 只有插入第一个节点的时候才会出现相等的情况
            // 而之所以插入第一个节点能够走到此处，是因为compare方法里面排除了current = header的情况
            current = compare(item, current) < 0 ? current.left : current.right;

            // 检查是否有两个红色的孩子； 如果是则翻转
            // 保证插入节点的父节点为黑色， 因为插入节点必为红色；此动作是为了避免违反红黑树第4条件
            if (current.left.color == RED && current.right.color == RED) {
                handleReorient(item);
            }
        }

        // 如果已经存在则插入失败
        if (current != nullNode) {
            return;
        }

        current = new RedBlackNode<>(item, nullNode, nullNode);

        // 附加到父级
        // 当parent为header时，此时为插入第一个节点，compare的结果为1，将第一个节点插入header.right
        // 也就造成header.right才是真正的根节点
        // 若要让header.left为真正的根节点，只需将 Line 190改为: return -1，且Line 219 改为: header.left.color = BLACK
        if (compare(item, parent) < 0) {
            parent.left = current;
        } else {
            parent.right = current;
        }
        // 为了不破坏条件4，必须将插入的节点置红，保证其儿子为黑
        handleReorient(item);
    }


    /**
     * 仅用于删除方法及其助手
     * bro: 兄弟节点
     */
    private RedBlackNode<T> bro;

    @Override
    public void remove(T x) {
        if (isEmpty()) {
            System.out.println("Empty Tree, what do you want?");
            return;
        }
        // 确定有才正式执行删除操作
        if (!contains(x)) {
            System.out.println("There is nothing you can delete!!!");
            return;
        }
        header.right.color = RED;
        // start ---------------------
        parent = grand = header;
        current = header.right;
        bro = header.left;

        while (compare(x, current) != 0) {
            if (isBlack(current.left) && isBlack(current.right)) {
                /*
                    当前节点C有两个黑儿子，则此时有三种情况
                    1. bro也有两个黑儿子，则直接翻转 c, p, bro的颜色
                    2. 如果bro的儿子之一是红儿子，那么根据这个儿子节点是哪一个，分别进行不同的旋转操作
                    注：无需考虑叶子节点，因为nullNode节点默认是黑色
                */
                if (isBlack(bro.left) && isBlack(bro.right)) {
                    parent.color = RED;
                    current.color = BLACK;
                    bro.color = BLACK;
                } else {
                    rotateByColor(x);
                }
                // 当前节点下移
                toTheNext(x);
            } else if (isBlack(current.left) || isBlack(current.right)) {
                /*
                    当前节点C的儿子之一是红色的，在这种情况下，落到下一层，得到新的c, bro, p; 此时有两种情况:
                    1. 如果C恰好落到红儿子，则 toTheNext
                    2. 如果不是这样，则此时bro必为红色，同样的c和p是黑色，此时需要旋转bro和p，并且翻转两者的颜色，
                        使得c的新父亲是红色的。理所当然的, c及其祖父将是黑色的。
                 */
                toTheNext(x);
                if (isBlack(current)) {
                    rotateSingle();
                }
            } else {
                // 当前节点c的两个儿子为红色，则 toTheNext
                toTheNext(x);
            }
        }
        /*
            当c.ele == x的时候，判断左右子树的情况
            1. 如果左子树和右子树都为nullNode，在直接删除: c = nullNode;
            2. 如果左子树为nullNode，则 c = c.right;
            3. 如果右子树为nullNode, 则 c = c.left;
            4. 如果左右子树都不为nullNode，则获取右子树最小的节点替换c，同时删除该节点。
         */
        if (current.left != nullNode && current.right != nullNode) {
            T ele = findMin(current.right).element;
            // 此处需要将c暂存，防止丢失
            RedBlackNode<T> temp = current;
            remove(ele);
            temp.element = ele;
        } else {
            if (compare(current.element, parent) < 0) {
                parent.left = (current.left != nullNode) ? current.left : current.right;
            } else {
                parent.right = (current.left != nullNode) ? current.left : current.right;
            }
        }
        // end ---------------------
        header.right.color = BLACK;
    }

    @Override
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            System.out.println("========== Start print tree ==========");
            // why? why not left?
            // Because Line 83:第一个节点 compare(item, current) = 0 => current.right
            printTree(header.right);
            System.out.println("\n========== End print tree ==========");
        }
    }

    // ================= 私有方法 ==================== //

    /**
     * 如果树T中存在含有项X的系欸但，那么返回true，否则返回false
     *
     * @param x 需要查找的项
     * @param t 以当前节点为根节点
     */
    private boolean contains(T x, RedBlackNode<T> t) {
        if (t == nullNode) {
            return false;
        }
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            // 如果目标值小于当前项值，则继续遍历左子树
            return contains(x, t.left);
        } else if (compareResult > 0) {
            // 如果目标值项大于当前项值，则继续遍历右子树
            return contains(x, t.right);
        } else {
            // 匹配
            return true;
        }
    }

    /**
     * 是否为黑节点
     *
     * @param t 当前节点
     * @return yes or no
     */
    private boolean isBlack(RedBlackNode<T> t) {
        return t.color == BLACK;
    }

    /**
     * 落至下一层
     *
     * @param x element
     */
    private void toTheNext(T x) {
        grand = parent;
        parent = current;
        bro = compare(x, current) < 0 ? current.right : current.left;
        current = compare(x, current) < 0 ? current.left : current.right;
    }

    /**
     * 执行单次或双次旋转的内部方法.
     * 因为结果附加到父级, 有四种情况.
     * 由handleReorient()调用。
     *
     * @param item   在handleReorient()中的item
     * @param parent 旋转子树的根的父级
     * @return 旋转子树的根
     */
    private RedBlackNode<T> rotate(T item, RedBlackNode<T> parent) {
        if (compare(item, parent) < 0) {
            return parent.left = compare(item, parent.left) < 0 ?
                    rotateWithLeftChild(parent.left) :
                    rotateWithRightChild(parent.left);
        } else {
            return parent.right = compare(item, parent.right) < 0 ?
                    rotateWithLeftChild(parent.right) :
                    rotateWithRightChild(parent.right);
        }
    }

    /**
     * 根据颜色进行旋转
     *
     * @param item 比较项
     */
    private void rotateByColor(T item) {
        if (compare(bro.element, parent) < 0) {
            // bro是左孩子
            if (!isBlack(bro.right)) {
                parent.left = rotateWithRightChild(parent.left);
            }
            grand = rotateWithLeftChild(parent);
        } else {
            // bro是右孩子
            if (!isBlack(bro.left)) {
                parent.right = rotateWithLeftChild(parent.right);
            }
            grand = rotateWithRightChild(parent);
        }
    }

    /**
     * 兄弟节点和父节点进行旋转
     */
    private void rotateSingle() {
        if (compare(bro.element, parent) < 0) {
            grand = rotateWithLeftChild(grand);
        } else {
            grand = rotateWithRightChild(grand);
        }
    }

    /**
     * 使用 compareTo 比较 item 和 t.element，但需要注意的是，
     * 如果 t是 header，则 item 总是更大。 如果 t 可能是标题，
     * 则调用此例程。如果t不可能是 header，请直接使用 compareTo
     */
    private int compare(T item, RedBlackNode<T> t) {
        if (t == header) {
            return 1;
        } else {
            return item.compareTo(t.element);
        }
    }

    /**
     * 如果节点有两个红色子节点，则在插入期间调用的内部方法。
     * 执行红黑翻转和旋转
     *
     * @param item 被插入的项目
     */
    private void handleReorient(T item) {
        // 颜色翻转
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        // 为了不破坏条件3，在出现父节点也是RED的情况下，需要做旋转
        if (parent.color == RED) {
            // 必须旋转
            grand.color = RED;
            if ((compare(item, grand) < 0) != (compare(item, parent) < 0)) {
                // 判断是否需要执行双旋转
                parent = rotate(item, grand);
            }
            // 进行左旋或者右旋
            current = rotate(item, great);
            current.color = BLACK;
        }
        // 根节点置黑，保证根节点为黑色
        header.right.color = BLACK;
    }

    /**
     * 单左旋
     *
     * @param k2 以当前节点作为根节点
     * @return node
     */
    private RedBlackNode<T> rotateWithLeftChild(RedBlackNode<T> k2) {
        RedBlackNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * 单右旋
     *
     * @param k1 以当前节点作为根节点
     * @return node
     */
    private RedBlackNode<T> rotateWithRightChild(RedBlackNode<T> k1) {
        RedBlackNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    /**
     * 找到最小节点
     *
     * @param t 以当前节点为根节点
     * @return minNode
     */
    private RedBlackNode<T> findMin(RedBlackNode<T> t) {
        while (t.left != nullNode) {
            t = t.left;
        }
        return t;
    }

    /**
     * 打印二叉树
     *
     * @param t 以当前节点为根节点
     */
    private void printTree(RedBlackNode<T> t) {
        if (t != nullNode) {
            printTree(t.left);
            System.out.print((t == header.right ? " root->" : " ") + t.element + ":" + t.color);
            printTree(t.right);
        }
    }

    /**
     * 红黑树节点
     *
     * @param <T> anyType
     */
    private static class RedBlackNode<T> {
        // 数据项
        T element;
        // 左孩子
        RedBlackNode<T> left;
        // 右孩子
        RedBlackNode<T> right;
        // 颜色
        int color;

        RedBlackNode(T theElement, RedBlackNode<T> lt, RedBlackNode<T> rt) {
            element = theElement;
            left = lt;
            right = rt;
            color = RedBlackTree.BLACK;
        }

        RedBlackNode(T theElement) {
            this(theElement, null, null);
        }
    }
}
