# QR Code Generator API

## Project Overview

This project is a Spring Boot-based REST API that generates QR codes from text input and stores them in AWS S3. The API provides a simple endpoint to generate QR codes and returns a publicly accessible URL where the generated QR code can be accessed.

### Technologies Used
- Java 21
- Spring Boot 3.4.5
- Google ZXing (QR Code generation)
- AWS SDK for Java (S3 integration)
- Maven (build tool)
- Docker (containerization)

## System Architecture

The application follows a clean architecture approach with clear separation of concerns:

```
com.frank1br.qrcode.generator/
├── controller/           # REST API endpoints
├── service/             # Business logic
├── infrastructure/      # External service implementations
├── ports/              # Interface definitions
└── dto/                # Data Transfer Objects
```

### Main Flow
1. User sends a POST request with text to generate QR code
2. Service layer generates QR code using ZXing library
3. Generated QR code is uploaded to AWS S3
4. Public URL of the uploaded QR code is returned to the user

## How to Run the Project

### Requirements
- Java 21
- Maven 3.9.6
- Docker (optional)
- AWS account with S3 access

### Environment Variables
Create a `.env` file with the following variables:
```bash
AWS_REGION=your-region
AWS_BUCKET_NAME=your-bucket-name
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
```

### Running Locally
1. Clone the repository
2. Set up environment variables
3. Run the application:
```bash
mvn spring-boot:run
```

### Running with Docker
1. Build the Docker image:
```bash
docker build -t qrcode-generator .
```

2. Run the container:
```bash
docker run -p 8080:8080 \
  -e AWS_REGION=your-region \
  -e AWS_BUCKET_NAME=your-bucket-name \
  -e AWS_ACCESS_KEY_ID=your-access-key \
  -e AWS_SECRET_ACCESS_KEY=your-secret-key \
  qrcode-generator
```

## External Integrations

### AWS S3 Integration
The application uses AWS S3 to store generated QR codes. The following IAM policy is required for the AWS credentials:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "s3:PutObject"
            ],
            "Resource": "arn:aws:s3:::your-bucket-name/*"
        }
    ]
}
```

### Public Access Configuration
To ensure the uploaded files are publicly accessible:
1. Enable public access on your S3 bucket
2. Configure bucket policy to allow public read access:
```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::your-bucket-name/*"
        }
    ]
}
```

## API Endpoints

### Generate QR Code
- **URL**: `/qrcode`
- **Method**: `POST`
- **Request Body**:
```json
{
    "text": "Your text here"
}
```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
```json
{
    "url": "https://your-bucket.s3.your-region.amazonaws.com/random-uuid.png"
}
```
- **Error Response**:
  - **Code**: 500 Internal Server Error
  - **Content**: Empty response body

## Testing

The project includes basic Spring Boot test infrastructure. To run the tests:

```bash
mvn test
```

## License and Contribution

This project is open source and available under the MIT License. Contributions are welcome! Please feel free to submit a Pull Request.

### Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request 
