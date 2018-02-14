#include <stdio.h>
#include <stdlib.h>
#include "learn.h"
int main(int argc, char** argv)
{
	char* learnName = NULL;
    FILE* learnFile = NULL;
    if (argc < 3)
    {
        printf("error\n");
        exit(0);
    }
    learnName = argv[1];
    learnFile = fopen(learnName, "r");
    if (learnFile == NULL)
    {
        printf("error\n");
        exit(0);
    }
    int i, j;
    fscanf(learnFile, "%d\n", &k);
    fscanf(learnFile, "%d\n", &n);
    x = (double**)malloc(n*sizeof(double *));
    xt = (double**)malloc((k + 1)*sizeof(double *));
    inner = (double**)malloc((k + 1)*sizeof(double *));
    y = (double*)malloc(n*sizeof(double));
    w = (double*)malloc((k + 1)*sizeof(double));
    outer = (double*)malloc((k + 1)*sizeof(double));
    for (i = 0; i < n; i++)
    {
        x[i] = (double*) malloc((k + 1)*sizeof(double));
    }
    for (i = 0; i < k + 1; i++)
    {
    	xt[i] = (double*) malloc((n)*sizeof(double));
    	inner[i] = (double*) malloc((k + 1)*sizeof(double));
    	
    }
    double entry;
    for (i = 0; i < n; i++)
    {
        x[i][0] = 1;
        xt[0][i] = 1;
        for (j = 1; j < k + 2; j++)
        {
            if (j == k + 1)
            {
            	fscanf(learnFile, "%lf\n", &y[i]);
            	break;
            } else
            {
            	fscanf(learnFile, "%lf,", &entry);
            	x[i][j] = entry;
            	xt[j][i] = entry;
            }
        }
    }
    multiply();
    FILE* testFile = NULL;
    testFile = fopen(argv[2], "r");
    if (testFile == NULL)
    {
        printf("error\n");
        exit(0);
    }
    fscanf(testFile, "%d\n", &n);
    double price;
    for (i = 0; i < n; i++)
    {
    	price = 0.0;
    	price += w[0];
    	for (j = 0; j < k; j++)
    	{
    		fscanf(testFile, "%lf,", &entry);
    		price += entry*w[j+1];
    	}
    	printf("%0.0lf\n", price);
    }
 
    free(outer);
    free(y);
    free(w);
    for (i = 0; i < n; i++)
    {
    	free(x[i]);
    }
    free(x);
    for (i = 0; i < k + 1; i++)
    {
    	free(xt[i]);
    	free(inner[i]);
    	free(augment[i]);
    }
    free(xt);
    free(inner);
    
    return 0;
}