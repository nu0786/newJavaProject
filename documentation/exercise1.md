# Exercise 1

1. Implement an endpoint that will be dedicated to our frontend application to display all **transfers** performed sorted by **value date** descending.
2. Relevant test cases can be implemented

**Additional information**:
You are free to design the API response object you want, frontend developer will agree with your proposal, however here some context about the frontend:
- The frontend expect the endpoint to be callable on `GET` `/api/transfer/list`
- The frontend will display transfers in a table, only **25** transfers will be displayed per pages
- The frontend will display only fields **Value date**, **From account number**, **To account number**, **Amount**, **Currency** in table columns
