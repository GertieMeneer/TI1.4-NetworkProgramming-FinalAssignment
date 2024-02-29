package tree;

// This class represents a tree node in the binary search tree
public class TreeNode
{
    private String key;
    private String value;
    private TreeNode left, right;

    public TreeNode(String key, String value)
    {
        this.key = key;
        this.value = value;
        left = right = null;
    }

    // Get the key of the node
    public String getKey()
    {
        return key;
    }

    // Set the key of the node
    public void setKey(String key)
    {
        this.key = key;
    }

    // Gets the value associated with the node
    public String getValue()
    {
        return value;
    }

    // Sets the value associated with the node
    public void setValue(String value)
    {
        this.value = value;
    }

    // Get the left child node
    public TreeNode getLeft()
    {
        return left;
    }

    // Set the left child node
    public void setLeft(TreeNode left)
    {
        this.left = left;
    }

    // Get the right child node
    public TreeNode getRight()
    {
        return right;
    }

    // Sets the right child node
    public void setRight(TreeNode right)
    {
        this.right = right;
    }
}
