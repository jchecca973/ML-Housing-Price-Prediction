double ** x;
double ** xt;
double ** inner;
double ** augment;
double * outer;
double * y;
double * w;
int k;
int n;

void inverse()
{
	augment = (double**)malloc((k+1)*sizeof(double));
	int a, b, c;
	double constant, pivot;
	for (a = 0; a < k+1; a++)
	{
		augment[a] = (double*)malloc((k+1)*sizeof(double));
	}
	for (a = 0; a < k + 1; a++)
	{
		for (b = 0; b < k + 1; b++)
		{
			if (a == b) augment[a][b] = 1;
			else augment[a][b] = 0;
		}
	}

	for (a = 0; a < k+1; a++)
	{
		pivot = inner[a][a];
		for (b = 0; b < k+1; b++)
		{
			inner[a][b] = inner[a][b]/pivot;
			augment[a][b] = augment[a][b]/pivot;
		}

		if (a < k)
		{
			
			for (b = a+1; b < k+1; b++)
			{
				constant = inner[b][a];
				for (c = 0; c < k+1; c++)
				{
					inner[b][c] = inner[b][c] - constant*inner[a][c];
					augment[b][c] = augment[b][c] - constant*augment[a][c];
				}
			}
		}	
	}
	
	for (a = 1; a < k+1; a++) // column
	{
		for (b = 0; b < a; b++) // row
		{
			constant = inner[b][a];
			for (c = 0; c < k+1; c++)
			{
				inner[b][c] = inner[b][c] - constant*inner[a][c];
				augment[b][c] = augment[b][c] - constant*augment[a][c];
			}
		}

	}

}

void multiply()
{
	int a, b, c;
	double temp;
	temp = 0;
	for (a = 0; a < k + 1; a++)
	{
		for (b = 0; b < k + 1; b++)
		{
			for (c = 0; c < n; c++)
			{
				temp += (xt[a][c] * x[c][b]);
			}
			inner[a][b] = temp;
			temp = 0.0;
		} 
	}
	inverse();
	for (a = 0; a < k + 1; a++)
	{
		for (b = 0; b < n; b++)
		{
			temp += (xt[a][b] * y[b]);
		}
		outer[a] = temp;
		temp = 0.0;
	}
	
	for (a = 0; a < k + 1; a++)
	{
		for (b = 0; b < k + 1; b++)
		{
			temp += (augment[a][b] * outer[b]);
		}
		w[a] = temp;
		temp = 0.0;
	}
}