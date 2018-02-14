# ML-Housing-Price-Prediction

This is an implementation of a simple machine learning algorithm to
predict house prices based on historical data.
For example, the price of the house (y) can depend on certain attributes of the house: number of
bedrooms (x1), total size of the house (x2), number of baths (x3), and the year the house was built (x4).
Then, the price of the house can be computed by the following equation:

y = w0 + w1.x1 + w2.x2 + w3.x3 + w4.x4

Given a house, we know the attributes of the house (i.e., x1, x2, x3, x4). However, we don’t know
the weights for these attributes: w0, w1, w2, w3 and w4. The goal of the machine learning algorithm in
this context is to learn the weights for the attributes of the house from lots of training data.
Let’s say we have N examples in the training data set that provide the values of the attributes and
the price. Let’s say there are K attributes. We can represent the attributes from all the examples in the
training data as a Nx(K + 1) matrix as follows, which we call X:

[
1, x(0,1) , x(0,2) , x(0,3) , x(0,4)
1, x(1,1) , x(1,2) , x(1,3) , x(1,4)
1, x(2,1) , x(2,2) , x(2,3) , x(2,4)
1, x(3,1) , x(3,2) , x(3,3) , x(3,4)
..
1, x(n,1) , x(n,2) , x(n,3) , x(n,4)
]

where n is N − 1. We can represent the prices of the house from the examples in the training data
as a Nx1 matrix, which we call Y .

[
y0
y1
..
yn
]

Similarly, we can represent the weights for each attribute as a (K + 1)x1 matrix, which we call W.

[
w0
w1
..
wk
]

The goal of this machine learning algorithm is to learn this matrix from the training data.
Now in the matrix notation, entire learning process can be represented by the following equation,
where X, Y , and W are matrices as described above.

X.W = Y

Using the training data, we can learn the weights using the below equation:

W = (XT.X)^(−1).XT.Y

where XT is the transpose of the matrix X, and (XT.X)^(−1) is the inverse of the matrix XT.X.

# Structure of the training data file

The first line in the training file is an integer that provides the number of attributes (K) in the
training set. The second line in the training data file is an integer (N) providing the number of
training examples in the training data set. The next N lines represent the N training examples. Each
line for the example will be a list of comma-separated double precision floating point values. The first
K double precision values represent the values for the attributes of the house. The last double precision
value in the line represents the price of the house.

An example training data file (train1.txt) is shown below:

4
7
3.000000,1.000000,1180.000000,1955.000000,221900.000000
3.000000,2.250000,2570.000000,1951.000000,538000.000000
2.000000,1.000000,770.000000,1933.000000,180000.000000
4.000000,3.000000,1960.000000,1965.000000,604000.000000
3.000000,2.000000,1680.000000,1987.000000,510000.000000
4.000000,4.500000,5420.000000,2001.000000,1230000.000000
3.000000,2.250000,1715.000000,1995.000000,257500.000000
