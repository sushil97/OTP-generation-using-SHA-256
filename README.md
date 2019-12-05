# Time-based-OTP-generation-using-SHA-256
This java program randomly generates OTP using SHA-256 encoding scheme. In this program system's date and time is taken as a input string. This string is then encoded using SHA-256 encoding and stored into a text file named hashFile.txt. The encoded string which is stored in hashFile.txt is then used to generate a 4 digit OTP. Since time is not static so always we will get random OTPs based on the time. 

The mapping of 64 bit hash code to 4 digit OTP is done in following way:
1. Compute bitwise XOR of first 8 characters from begining and last 8 characters from end.
2. Compute bitwise XOR of two results came from first step and concatinate the result into OTP string.
3. Repeat this step untill all the 64 characters are traversed.
4. Now OTP verification step will begin here user will enter the four digit OTP provided. If hash of OTP entered by user matches to that of stored in hashFile.txt then it is verified.

# Installation
1. Clone or download the project.
2. Import as a java project in eclipse.
3. Run the program.

Any improvements would be highly appretiated ! 
