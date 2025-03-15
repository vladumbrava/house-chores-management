# house-chores-management

- create a house
- delete a house
- update a house
- read a house's details (number of members, MembersList, pointsPerHouse)
- add member
- remove member
- update a member
- read a member's details (firstName, lastName, age, points, choresInProgress)
- add a chore
- delete a chore
- update a chore (assign a different member to it / change the duetime / change the title / change the indications
- a chore can have a random member assigned or we can choose which member to assign to the chore
- (calendar of the chores)
- points for each chore, a member is assigned with points
- (leaderboard of members)
- if the chore is not completed, the member assigned to that chore, loses that amount of points

Chore class, House class, Member class

Chore: Title, Description, Deadline, PointsForCompleting, MemberAssigned, Status(in_progress, completed, time_passed)

House: Name, ChoresList, MembersList (house can be a repository)

Member: FirstName, LastName, Age, Points 