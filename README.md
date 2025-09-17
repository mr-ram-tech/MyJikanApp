# MyJikanApp

An Android anime discovery app built with Jetpack Compose that displays top anime from the Jikan API with detailed information and trailer playback.

## Features Implemented

### Core Functionality
- **Anime List Screen**: Displays top anime in a responsive grid layout with posters, titles, episodes, and ratings
- **Anime Detail Screen**: Comprehensive anime information including:
  - Trailer playback with embedded YouTube player (when available)
  - Poster image fallback when no trailer exists
  - Synopsis/plot description
  - Genres
  - Main cast information
  - Episode count and rating
- **Navigation**: Seamless navigation between list and detail screens

### Technical Implementation
- **MVVM Architecture**: Clean separation with ViewModel, Repository, and Room database
- **Third-party Video Player**: Integrated YouTube Android Player for reliable trailer playback
- **Offline Support**: Room database caches anime data for offline viewing
- **Error Handling**: Graceful error management with retry mechanisms
- **Loading States**: Circular progress indicators during data loading

### Libraries Used
- **Jetpack Compose**: Modern UI toolkit
- **Retrofit**: API networking
- **Room**: Local database
- **Coil**: Image loading
- **YouTube Android Player**: Video playback
- **Navigation Compose**: Screen navigation
- **Coroutines**: Asynchronous programming

## Assumptions Made

### API Integration
- Jikan API v4 endpoints are stable and accessible
- Anime detail responses include trailer information when available
- Character data is sufficient for "Main Cast" display (limited to top 10)
- API rate limits are not restrictive for normal usage

### User Experience
- Users prefer embedded video playback over external app launching
- Grid layout with 2 columns works well on various screen sizes
- Loading indicators provide adequate feedback during network operations
- Snackbar retry mechanism is intuitive for error recovery

### Technical Decisions
- Room database provides sufficient offline capability for basic browsing
- WebView fallback ensures compatibility when YouTube player fails
- Simple error messages are more user-friendly than technical details
- Caching anime list is prioritized over individual detail caching

## Known Limitations

### Video Playback
- **YouTube Dependencies**: Trailer playback relies on YouTube's embedded player
- **Network Requirements**: Video streaming requires stable internet connection
- **Device Compatibility**: Some older devices may have limited video codec support
- **API Restrictions**: YouTube may block certain embed requests in WebView

### Data Management
- **Limited Offline Support**: Only anime list is cached; details require internet
- **No Pagination**: Currently loads only the first page of top anime
- **Character Data**: Main cast is limited to first 10 characters from API
- **Image Caching**: Poster images are not persisted offline

### API Limitations
- **Rate Limiting**: No built-in rate limiting protection
- **Error Recovery**: Limited retry logic for failed API calls
- **Data Freshness**: No automatic refresh mechanism for stale data
- **Network Detection**: No offline/online state monitoring

### User Interface
- **No Search**: Users cannot search for specific anime
- **No Favorites**: No bookmarking or favorites functionality
- **Limited Filtering**: No genre or rating-based filtering
- **No Pull-to-Refresh**: Manual refresh only through retry mechanism

### Performance Considerations
- **Memory Usage**: Large image loading without optimization
- **Database Size**: No cleanup mechanism for old cached data
- **Network Efficiency**: No request batching or optimization
- **Image Loading**: No progressive loading or placeholder handling


## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Minimum SDK 24 (Android 7.0)
- Internet connection for API access
Notes:
- Compile SDK: 36 — ensure Android SDK API 36 is installed before building.

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Build and run on device/emulator
