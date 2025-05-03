FROM node:alpine

WORKDIR /app

# Install dependencies
RUN apk update && apk add --no-cache curl bash
COPY . .
RUN npm install --legacy-peer-deps

# Build the application
RUN npm run build
# Expose the port the app runs on
EXPOSE 4200
# Start the application
CMD ["npm", "start", "--", "--host", "0.0.0.0", "--port", "4200"]

