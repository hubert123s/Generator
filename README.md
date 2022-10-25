# End points
### GET

http://localhost:8080/generator - returns a list of active generators

### POST

http://localhost:8080/generator - adds new generator parameters

### Json example

{
  "min": "1",
  "max": "3",
  "numbersOfString": "5",
  "chars": "a,b,c,n,d,j,D,s,N"
}

### GET

http://localhost:8080/generator/{id} - returns the contents of a specific generator
