# Refutation Theorem Prover
A Object-oriented Java implementation of a simple logic theorem prover by refutation.
Code was built in 2007 during a Post-graduate course in CS.

## Usage
java -jar "RefutationProof.jar" <filename>

<filename> is a plan-text file containg in each line a clausal form. Last clause must be the negated conclusion (the opposite of what we want to prov).

-Variables and relations are allowed. 

-Comments must start with #

The refutation algorithm will read the clauses and build a proof tree. The theorem is proved whenever a contraction is found.

## Example
