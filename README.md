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

BigInteger used because 21! exceeds long.


