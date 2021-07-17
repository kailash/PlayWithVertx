from tabulate import tabulate
import pandas as pd
import sys
import os
 
class Coverage():
    def __init__(self, location):
        super().__init__()
        self.location = location
        
        
    def calculateCoverage(self):
        table_MN = pd.read_html(self.location)
        df = table_MN[0]
        # print(df.columns)
        df=df.drop(['Missed Instructions','Missed Branches'],axis=1)
        print((tabulate(df, headers='keys', tablefmt='psql')))
        val=df.tail(1)
        instCoverage=self.getCoverage(val,'Cov.')
        branchCoverage=self.getCoverage(val,'Cov..1')
        print('inst  : '+instCoverage +' branch : '+branchCoverage)
        total=self.calculateTotalCoverage(int(instCoverage[:-1]),int(branchCoverage[:-1]))
        print('total : '+str(total)+'%')
        return total

    def getCoverage(self,lastRow,attr):  
        instructionCoverage=lastRow[attr].values[0]
        return instructionCoverage

    def calculateTotalCoverage(self,instructionsCoverage,branchCoverage):
        return (instructionsCoverage+branchCoverage)/2
 
 
def main(file):
    coverage=Coverage(file).calculateCoverage()
    print("::set-output name=coverage::"+str(coverage))
    return coverage
    
if __name__ == "__main__":
    print('starting...'+ str(sys.argv[0]) +" : "+str(sys.argv[1]))
    main(sys.argv[1])

