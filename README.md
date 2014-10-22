WordRank
========

Word ranking algorithm

Consider a "word" as any sequence of letters A-Z (not limited to just "dictionary words"). For any word with at least two different
letters, there are other words composed of the same letters but in a different order (for instance, stationarily/antiroyalist, which
happen to both be dictionary words; for our purposes "aaiilnorstty" is also a "word" composed of the same letters as these two).
We can then assign a number to every word, based on where it falls in an alphabetically sorted list of all words made up of the same
set of letters. One way to do this would be to generate the entire list of words and find the desired one, but this would be slow if
the word is long.
Write a program which takes a word as a command line argument and prints to standard output its number. Do not use the method above of
generating the entire list. Your program should be able to accept any word 25 letters or less in length (possibly with some letters
repeated), and should use no more than 1 GB of memory and take no more than 500 milliseconds to run.
Sample words, with their rank:
ABAB = 2
AAAB = 1
BAAA = 4
QUESTION = 24572
BOOKKEEPER = 10743

Solution
Takes command link argument of a word of up two 25 letters and returned the alphabetical rank
of all the permutations of the letters in the input word.
Solution found using additive properties of the alphabetical rank. The program narrows down the
possible solutions by calculating the number of permutations for the set of letters, takes the
first letter from the word in that set, calculates it's alphabetical rank among the set of letters
and calculates the first possible occurrence of that letter. If we do this for the rest of the
letters, shortening our set but one (the first letter in the previous set) we will eventually learn
the rank of the word in the total number of permutations.

* BigInteger used because 21! exceeds long.
*
* Important equation:
* n=number of letters
* n1=number of letters=letter1 (e.g. "A")
* n2=number of letters=letter2 (e.g. "B")
* ....
* N_permutations=n!/(sum(n1)!*sum(n2)!...sum(nx)!)
*
* Example:
* BOOTH (alphabetized BHOOT)
* 01234
* n=5
* nB=1
* nO=2
* nH=1
* nT=1
*
* First letter:
* N_permutations=n!/(nB!*nH!*nO!*nT!)
* N_permutations=5!/(1!*1!*2!*1!)=120/2=60
* Find where words beginning with rank: BHOOT
* 01234
* B is 0 of 4, with 0 being first.
* Words beginning with B must rank if the first 20%(1/5) of possible permutations.
* First 20% is 0-12
* rank_B=N_permutations*alpha_order_B
* rank_B=60*0=0
* Pop "B" from list.
*
* Second letter:
* N_permutations=(n-1)!/(nH!*nO!*nT!)
* N_permutations=4!/(1!*2!*1!)=24/2=12
* Find where words beginning with rank: HOOT
* 0123
* O is 1 of 3.
* Words beginning with 0 must rank if the second 25%(1/4) of possible permutations.
* Second 25% is 4-8
* rank_O_0=N_permutations*alpha_order_O_0
* rank_O_0=12*1/4=3
* Pop "O_0" from list.
*
* Third letter:
* N_permutations=(n-2)!/(nH!*(nO-1)!*nT!)
* N_permutations=3!/(1!*1!*1!)=6/1=6
* Find where words beginning with rank: HOT
* 012
* O is 1 of 2.
* Words beginning with 0 must rank if the second 33%(1/3) of possible permutations.
* Second 33% is 2-3
* rank_O_1=N_permutations*alpha_order_O_1
* rank_O_1=6*1/3=2
* Pop "O_1" from list.
*
* Fourth letter:
* N_permutations=(n-3)!/(nH!*nT!)
* N_permutations=2!/(1!*1!)=2/1=2
* Find where words beginning with rank: HT
* 01
* T is 1 of 1.
* Words beginning with 0 must rank if the second 50%(1/2) of possible permutations.
* Second 50% is 1
* rank_T=N_permutations*alpha_order_T
* rank_T=2*1/2=1
* Pop "T" from list.
*
* Fifth letter:
* N_permutations=(n-4)!/(nH!)
* N_permutations=1!/(1!)=1
* Find where words beginning with rank: H
* 0
* H is 0 of 0.
* rank_H=N_permutations*alpha_order_H
* rank_H=1*0/1
* Pop "H" from list.
*
* Rank(BOOTH)=rank_B+rank_O_0+rank_O_1+rank_T+rank_H
* Rank(BOOTH)=0+3+2+1+0=6+1(because there is no rank 0)
* Rank(BOOTH)=7
* 
