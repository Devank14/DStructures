public class Tries {

    // The absolute root
    private TrieNode root;

    Tries() {
        root = new TrieNode('\0');
    }

    public static void main(String args[]){
        Tries trie = new Tries();
        trie.add("not");
        trie.add("note");
        trie.add("news");
        System.out.println(trie.search("not"));
        trie.remove("not");
        System.out.println(trie.search("not"));

    }

    public void add(String word) {
        add(root, word);
    }

    private void add(TrieNode node, String word) {

        if (word.length() == 0) {
            node.isTerminating = true;
            return;
        }

        int firstIndex = word.charAt(0) - 'a';
        TrieNode firstNode = node.children[firstIndex];
        if (firstNode == null) {
            // Create a node and put into it.
            firstNode = new TrieNode(word.charAt(0));
            node.children[firstIndex] = firstNode;
        }
        // Node already exits, now we just have to move to the next character.
        add(firstNode, word.substring(1));
    }

    public boolean search(String word) {
        return search(root, word);
    }

    private boolean search(TrieNode node, String word) {

        if (word.length() == 0) {
            if (node.isTerminating)
                return true;
            else
                return false;
        }
        int firstIndex = word.charAt(0) - 'a';

        if (node.children[firstIndex] == null) {
            return false;
        }
        boolean searchNext = search(node.children[firstIndex], word.substring(1));

        return searchNext;
    }

    public void remove(String word) {
        /*
         * We first have to find the word- If the word exits,
         * just mark it's isTerminating as false.
         * No we do not have to increase complexity
         * by calling search function first.
         */

        remove(root, word);
    }

    public void remove(TrieNode node, String word){

        if(word.length() == 0){
            node.isTerminating = false;
            return;
        }
        int firstIndex = word.charAt(0) - 'a';
        TrieNode firstNode = node.children[firstIndex];

        if(firstNode ==  null) return;

        remove(firstNode, word.substring(1));
    }

    public class TrieNode {
        private char data;
        private boolean isTerminating;
        private TrieNode[] children;

        TrieNode(char data) {
            this.data = data;
            this.isTerminating = false;
            this.children = new TrieNode[26];
        }

        public void setData(char data) {
            this.data = data;
        }

        public void setTerminating(boolean isTerminating) {
            this.isTerminating = isTerminating;
        }

        public void setChildren(TrieNode[] children) {
            this.children = children;
        }

        public char getData() {
            return data;
        }

        public boolean isTerminating() {
            return isTerminating;
        }

        public TrieNode[] getChildren() {
            return children;
        }
    }

}