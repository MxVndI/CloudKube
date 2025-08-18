# DTO Summary for CloudKube API

This document provides an overview of all DTOs (Data Transfer Objects) used across the CloudKube API endpoints.

## Architecture Overview

The application follows a clean architecture pattern where:
- **Controllers** handle HTTP requests/responses and delegate business logic to services
- **Services** contain all business logic and DTO conversion logic
- **DTOs** are used for data transfer between layers
- **Models** are used only for database operations

## Authentication Endpoints

### POST /register
- **Request**: `UserCreateDto`
- **Response**: `AuthResponseDto`
- **Service**: `AuthService.registerUserDto()`

### POST /login  
- **Request**: `UserCreateDto`
- **Response**: `AuthResponseDto`
- **Service**: `AuthService.loginUserDto()`

## User Management Endpoints

### GET /users
- **Response**: `List<UserListDto>`
- **Service**: `UserService.getAllUsersDto()` or `UserService.getUsersByUsernameDto()`

### GET /users/{id}
- **Response**: `UserProfileDto`
- **Service**: `UserService.getUserProfileDto()`

### GET /users/{id}/friends
- **Response**: `List<UserFriendDto>`
- **Service**: `UserService.getUserFriendsDto()`

### POST /users/add-friend
- **Request**: `FriendshipRequestDto` (via query param)
- **Response**: `Map<String, String>` (legacy - consider updating to use DTO)

### GET /users/create-post
- **Response**: `PostDto`
- **Service**: Direct DTO creation in controller

## Post Management Endpoints

### POST /users/posts
- **Request**: `PostDto`
- **Response**: `PostResponseDto`
- **Service**: `PostService.createPostDto()` or `PostService.createPostDtoWithError()`

## Chat Endpoints

### GET /chat
- **Response**: `List<MessageDto>`
- **Service**: Direct DTO conversion in controller

### WebSocket /chat.sendMessage
- **Request**: `Message` (model)
- **Response**: `MessageDto`

### WebSocket /chat.addUser
- **Response**: `Message` (model)

## Image Management Endpoints

### POST /image
- **Request**: `MultipartFile`
- **Response**: `ImageUploadResponseDto`
- **Service**: `ImageService.uploadImageDto()`

### GET /images/{fileName}
- **Response**: `byte[]` (image data)
- **Service**: `ImageService.getUserImage()`

## DTO Details

### UserCreateDto
```java
{
    "username": "string",
    "password": "string"
}
```

### UserProfileDto
```java
{
    "id": "integer",
    "username": "string", 
    "imageUrl": "string",
    "friendshipStatus": "string",
    "isCurrentUser": "boolean"
}
```

### UserListDto
```java
{
    "id": "integer",
    "username": "string",
    "imageUrl": "string"
}
```

### UserFriendDto
```java
{
    "id": "integer",
    "username": "string",
    "imageUrl": "string"
}
```

### PostDto
```java
{
    "content": "string"
}
```

### PostResponseDto
```java
{
    "userId": "integer",
    "content": "string",
    "username": "string"
}
```

### MessageDto
```java
{
    "id": "integer",
    "content": "string",
    "timestamp": "instant",
    "username": "string"
}
```

### FriendshipRequestDto
```java
{
    "userId": "integer",
    "message": "string"
}
```

### ImageUploadResponseDto
```java
{
    "message": "string",
    "userId": "integer",
    "imageUrl": "string"
}
```

### AuthResponseDto
```java
{
    "message": "string",
    "username": "string",
    "token": "string"
}
```

### ErrorResponseDto
```java
{
    "error": "string",
    "message": "string"
}
```

## Service Layer Methods

### UserService
- `getUserProfileDto(Integer userId, Integer currentUserId)` → `UserProfileDto`
- `getAllUsersDto()` → `List<UserListDto>`
- `getUsersByUsernameDto(String username)` → `List<UserListDto>`
- `getUserFriendsDto(Integer userId)` → `List<UserFriendDto>`

### PostService
- `createPostDto(Integer userId, String content)` → `PostResponseDto`
- `createPostDtoWithError(String errorMessage)` → `PostResponseDto`

### ImageService
- `uploadImageDto(MultipartFile image, Integer userId)` → `ImageUploadResponseDto`
- `createErrorResponseDto(String errorMessage)` → `ImageUploadResponseDto`

### AuthService
- `loginUserDto(UserCreateDto user)` → `AuthResponseDto`
- `registerUserDto(UserCreateDto user)` → `AuthResponseDto`

## Implementation Status

✅ **Fully Implemented with Service Layer:**
- Authentication endpoints (login/register)
- User profile endpoint
- User listing endpoint
- User friends endpoint
- Post creation endpoint
- Image upload endpoint
- Chat messages endpoint

⚠️ **Partially Implemented:**
- Friendship management (still returns Map - consider updating)

## Benefits of New Architecture

1. **Separation of Concerns**: Controllers only handle HTTP, services handle business logic
2. **Reusability**: Service methods can be used by multiple controllers
3. **Testability**: Business logic is easily testable in isolation
4. **Maintainability**: Changes to business logic only affect services
5. **Consistency**: All DTO conversions follow the same pattern
6. **Clean Controllers**: Controllers are now thin and focused

## Notes

- All DTOs use Lombok annotations for getters, setters, and constructors
- Most DTOs contain only essential fields to minimize data transfer
- Controllers now delegate all business logic to services
- Image URLs are automatically constructed as `/images/{filename}` format
- Friendship status and user relationships are properly mapped to DTOs
- Error handling now uses structured DTOs instead of generic Maps
- Service layer handles all DTO conversions and business logic
