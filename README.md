# MINIMIZATION ALGORITHM

**This project was made by:**  
- Isabella Ocampo  
- Maria Laura Tafur  

**Group:** C2566 SI2002-5730  

For the implementation of this project, the operating system used was **Windows 10**, the programming language was **Java**, and the main development tool was **Visual Studio Code**. These tools provided a stable environment for coding, testing, and running the program, ensuring compatibility and efficiency during the implementation process.

This **Java program** identifies equivalent states in a deterministic finite automaton (DFA). It uses standard Java libraries like `import java.util.*;` which are used for input handling (`Scanner`), collections (`List`, `Set`, `HashSet`), and stream processing.

---

## INSTRUCTIONS

1. Download the code and execute it in the IDE of your choice.  
2. Insert the data the program needs, including:  
   - Cases (how many DFAs are going to be evaluated)  
   - Number of states  
   - Alphabet symbols  
   - Final (accepting) states  
   - Transition table  
3. The program will return those states that are equivalent.

---

## EXPLANATION

### 1. Input Parsing (solve function)

The program starts by reading:  
- The number of states  
- The alphabet symbols (input characters)  
- The list of final (accepting) states  
- The transition table, which defines how each state moves to another state for each symbol  

This sets up the structure of the DFA in memory.

### 2. Initial Marking

Once the DFA is loaded, the algorithm marks all pairs of states where one is final and the other is not. These pairs are considered immediately distinguishable because they clearly do not accept the same language.

### 3. Iterative Refinement

After the initial marking, the algorithm enters a loop where it checks all remaining (unmarked) state pairs. For each pair, it checks how both states behave for each input symbol:  
- If the transitions from both states lead to another pair that is already marked as distinguishable, then the current pair is also marked.  
- This is because their future behavior differs on that input, making them distinguishable as well.  

This process is repeated until no more new pairs are marked, meaning all distinguishable states have been identified.

### 4. Output of Equivalent States

At the end, any state pairs that remain unmarked are considered equivalent:  
- They behave identically for all possible input strings.  
- They can be merged in a minimized DFA without affecting language recognition.  

These equivalent pairs are then printed as the output.

