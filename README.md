# Refutation Theorem Prover
A Object-oriented Java implementation of a simple logic theorem prover by refutation.
Code was built in 2007 during a Post-graduate course in CS.

## Usage
```
java -jar "RefutationProof.jar" <filename>
```
- **filename** is a plan-text file containg in each line a clausal form. Last clause must be the negated conclusion (the opposite of what we want to prov). 

- **Variables** are are allowed and must start with a **Capital letter**.

- **Relations** are allowed. Example relation(a, b).

- **Comments** must start with #

## Algorithm

The refutation algorithm will read the clauses and build a proof tree. The theorem is proved whenever a contraction is found.
**Output** is a YES/NO answer, the proof tree and the variables table resolution. 

See, for instance, http://intrologic.stanford.edu/lectures/lecture_06.pdf for further reference on clausal form and refutation proofs.

## Example 1

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

## Example 2

**Input file content (Example2.txt)**
```
# Facts:
# Tony, Mike and John belong to the Alpine Club.
# Every member of the Alpine Club who is not a skier is a mountain climber.
# Mountain climbers don't like the rain.
# Anyone who doesn't like snow is not a skier.
# Mike doesn't like what Tony likes and he likes everything Tony doesn't like.
# Tony likes rain and snow.
# Query: 
# “Is there a member of the Alpine Club who is a mountain climber but not a skier?”

#### Premises
alpine(tony)
alpine(john)
alpine(mike)
~alpine(X) v skier(X) v climber(X)
~climber(Y) v ~likes(rain, Y)
likes(snow, Z) v ~skier(Z)
~likes(W, tony)  v ~likes(W, mike)
likes(U, tony) v likes(U, mike)
likes(rain, tony)
likes(snow, tony)
### Negated Conclusion
~alpine(E) v ~climber(E) v skier(E)
```

**Output**
```
Result: YES
Proof tree

nil
│  ├──skier(mike)
│  │  ├──~alpine(E) v skier(E) v ~alpine(E) v skier(E)
│  │  │  ├──~alpine(E) v ~climber(E) v skier(E)
│  │  │  └──~alpine(X) v skier(X) v climber(X)
│  │  └──alpine(mike)
│  └──~skier(mike)
│  │  ├──~likes(snow , mike)
│  │  │  ├──likes(snow , tony)
│  │  │  └──~likes(W , tony) v ~likes(W , mike)
│  │  └──likes(snow , Z) v ~skier(Z)


Instances:
W / snow
E / mike
Z / mike
X / mike

```

