# Library API

A small REST service that models cities, library branches, members and
books. 

See: https://github.com/NyuSD/library-cli

| Entity | Key fields | Relationships |
|--------|------------|---------------|
| **City**           | `id`, `name`, `state`, `population` | 1-to-many **LibraryBranch** |
| **LibraryBranch**  | `id`, `name`, `streetAddress`, `openHours` | many-to-1 **City** • many-to-many **Book** • many-to-many **Member** (visits) |
| **Member**         | `id`, `firstName`, `lastName`, `email` | many-to-1 **City** • many-to-many **Book** (borrowed) • many-to-many **LibraryBranch** (visits) |
| **Book**           | `id`, `title`, `author`, `isbn` | many-to-many **LibraryBranch** & **Member** |

---

## 1. Prerequisites

* JDK 17 +  
* Maven 3.9 + (or just use the wrapper `./mvnw`)  
* A running SQL server (tested with **PostgreSQL 15**)

---

## 2. Configuration

Edit `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url:  jdbc:postgresql://localhost:5433/library_db             # ← change info here
    username: root
    password: secret
```

## 3. Running

```
./mvnw spring-boot:run
```
The server starts on http://localhost:8080

Simple data seeding in PS tested in VSC on windows, you can also use curl or postman if you like

```
$base = 'http://localhost:8080'
function post($path,$obj){
  Invoke-RestMethod -Uri "$base$path" -Method Post -ContentType 'application/json' -Body ($obj|ConvertTo-Json -Compress)
}

$city   = post '/api/cities'   @{name='Avalon';state='NL';population=200000}

$branch = post '/api/branches' @{
  name='Avalon Central';streetAddress='123 Water St';openHours='09:00-17:00';city=@{id=$city.id}
}

$book   = post '/api/books'    @{
  title='The Pragmatic Programmer';author='Andrew Hunt';isbn=[guid]::NewGuid().ToString()
}

$member = post '/api/members'  @{
  firstName='Jamie';lastName='Keys';email=("jamie_{0}@example.com" -f (Get-Random));city=@{id=$city.id}
}

Invoke-RestMethod -Method Put "$base/api/branches/$($branch.id)/books/$($book.id)"
Invoke-RestMethod -Method Put "$base/api/members/$($member.id)/borrow/$($book.id)"
Invoke-RestMethod -Method Put "$base/api/members/$($member.id)/visit/$($branch.id)"

"✔  Seed data loaded:"
"   City    id = $($city.id)"
"   Branch  id = $($branch.id)"
"   Book    id = $($book.id)"
"   Member  id = $($member.id)"
```
