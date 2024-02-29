package tree;

import java.util.ArrayList;
import java.util.List;

// This class represents a binary search tree
public class BinarySearchTree
{
    private TreeNode root;      // Root node of the tree

    public BinarySearchTree()
    {
        root = null;
    }

    //Method to insert a key-value pair into the tree
    public void put(String key, String value)
    {
        root = put(root, key, value);
    }

    // Recursive method to insert a key-value pair into a subtree rooted at node
    public TreeNode put(TreeNode node, String key, String value)
    {
        if (node == null)
        {
            return new TreeNode(key, value);
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0)
        {
            node.setLeft(put(node.getLeft(), key, value));
        }
        else if (cmp > 0)
        {
            node.setRight(put(node.getRight(), key, value));
        }
        else
        {
            node.setValue(value);
        }

        return node;
    }

    // Get the value associated with a given key
    public String get(String key)
    {
        TreeNode node = get(root, key);

        if (node == null)
        {
            return null;
        }

        return node.getValue();
    }

    // Recursive method to retreive the node associated with a given key in a subtree rooted at node
    private TreeNode get(TreeNode node, String key)
    {
        if (node == null)
        {
            return null;
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0)
        {
            return get(node.getLeft(), key);
        }
        else if (cmp > 0)
        {
            return get(node.getRight(), key);
        }
        else
        {
            return node;
        }
    }

    // Checks if the tree contains a given key
    public boolean containsKey(String key)
    {
        return get(key) != null;
    }

    // Retrieves all keys in the tree
    public List<String> getAllKeys()
    {
        List<String> keys = new ArrayList<>();
        getAllKeysHelper(root, keys);
        return keys;
    }

    // Recursive helper method to traverse the tree in-order and collect keys
    private void getAllKeysHelper(TreeNode node, List<String> keys)
    {
        if (node == null)
        {
            return;
        }

        getAllKeysHelper(node.getLeft(), keys);
        keys.add(node.getKey());
        getAllKeysHelper(node.getRight(), keys);
    }
}

