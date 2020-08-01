# Implemented
- [x] Use the Location Service (Core Location as an example on iOS) and the Yelp API to load restaurants in the current area of the device.
- [x] The main view will contain a stack of cards.
- [x] Each card should display the name of the restaurant, the restaurant image,
and the rating.
- [x] The viewer should have two buttons, one for the next card and one for the previous one.
- [x] The next button dismisses the current card at the top of the stack. Dismissal should
animate the card offscreen to the left revealing the next card.
- [x] The previous button brings back the previously shown card. This should animate the
previous card back on screen from the left.
- [x] (Minimum sdk is minSdkVersion 16) SDK Level >= 23 and multiple screen sizes for Android.)
- [x] Your implementation should automatically load more results from the Yelp API when the user reaches near the end of the card stack.
- [x] The feed should allow the user to browse endlessly. When the user nears the end of the card stack, the app should automatically load more results from the Yelp API. This should load seamlessly in the background with minimal impact to the user experience.
- [x] (BONUS) Add an input field to change the query from just restaurants to anything.
- [x] (BONUS) Add a toggle to favorite each restaurant and persist the favorited values.


## Not implemented:
- (BONUS) Your implementation works in landscape mode.
- When a new search business query is entered ViewPager still shows the preloaded images for 2 cards.

## Info
- Implemented Card stack using ViewPager.
- Implemented network calls using retrofit.
- Tried best to keep MVVM pattern, but got deviated to make a working app.
- Used Room to persist favorited values.
- Cards can change through the 2 buttons or swipe action.

- Spent around 6, 7 hours
- Passing latitude and longitude as query params was causing a 400 errcode, spent most of my time fixing the issue.

## Gifs

### 1.Infinite list
![InfiniteList Demo](demo/1.InfiniteList.gif)

![LeftRightSwipe Demo](demo/2.LeftRightSwipe.gif)

![3.InputSearch Demo](demo/3.InputSearch.gif)

![PersistentFavorite Demo](demo/4.PersistentFavorite.gif)

