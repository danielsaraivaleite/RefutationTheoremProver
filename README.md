# Refutation Theorem Prover
A Object-oriented Java implementation of a simple logic theorem prover by refutation.
Code was built in 2007 during a Post-graduate course in CS.

## Usage
```
java -jar "RefutationProof.jar" <filename>
```
<filename> is a plan-text file containg in each line a clausal form. Last clause must be the negated conclusion (the opposite of what we want to prov). 

-Variables are are allowed and must start with a **Capital letter**.

- relations are allowed. Example relation(a, b).

-Comments must start with #

The refutation algorithm will read the clauses and build a proof tree. The theorem is proved whenever a contraction is found.
**Output** is a YES/NO answer, the proof tree and the variables table resolution. 

See, for instance, http://intrologic.stanford.edu/lectures/lecture_06.pdf for further reference on clausal form and refutation proofs.

## Example

**Input file content (Example1.txt)**
```
# Example
# Source: http://scom.hud.ac.uk/scomtlm/cam326/logic/node9.html
# Dave and Fred are members of a dancing club in which no member can 
# both  waltz  and jive.   Fred"s dad can"t waltz and  Dave  can  do 
# whatever  fred can"t do.  If a child can do something, then their 
# parents can do it also.
#
#Prove that there is a member of the dancing club who can"t jive.

m(dave)
m(fred)
~m(X) v ~can(X,waltz) v ~can(X,jive)
p(dadfred,fred)       
~can(dadfred,waltz)
can(dave,C1) v can(fred,C1) 
~p(Father,Son) v ~can(Son,C2) v can(Father,C2)

# Negated conclusion
~m(Person) v can(Person,jive)
```

**Output**
```
Result: YES
Proof tree

nil
│  ├──~m(dave)
│  │  ├──~m(dave) v can(fred , waltz)
│  │  │  ├──~m(X) v ~m(X) v ~can(X , waltz)
│  │  │  │  ├──~m(Person) v can(Person , jive)
│  │  │  │  └──~m(X) v ~can(X , waltz) v ~can(X , jive)
│  │  │  └──can(dave , C1) v can(fred , C1)
│  │  └──~can(fred , waltz)
│  │  │  ├──~p(dadfred , Son) v ~can(Son , waltz)
│  │  │  │  ├──~p(Father , Son) v ~can(Son , C2) v can(Father , C2)
│  │  │  │  └──~can(dadfred , waltz)
│  │  │  └──p(dadfred , fred)
│  └──m(dave)


Instances:
Father / dadfred
Person / dave
C2 / waltz
C1 / waltz
X / dave
Son / fred
```
