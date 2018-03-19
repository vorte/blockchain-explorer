############################################

Build application: ./gradlew build

Run application: ./gradlew bootRun

#############################################

Note that the application will only return up to 250 unspent outputs. The version of the blockchain client used
does not provide the option to specify the number of outputs required. The api seems to allow requesting up to 
1000 outputs but again there is no option to provide an offset so getting the full list is not possible. 

Alternatively could use the /rawaddr endpoint to get the complete list of transactions, iterate over those, find the 
unspent ones and return them. This would work but using the unspent outputs endpoint seemed cleaner to me.