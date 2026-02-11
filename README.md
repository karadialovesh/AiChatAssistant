# AI Chat Assistant (Local RAG)

I built this project to explore how RAG (Retrieval Augmented Generation) actually works under the hood, without relying on external APIs like OpenAI or heavy frameworks.

It is a Spring Boot application that lets you upload a PDF and then chat with it using a local LLM (via Ollama).

How It Works
The app has three main parts:
1. Ingestion: Scans a PDF, extracts text using Apache PDFBox, and splits it into semantic chunks (using a custom sentence-aware splitter I wrote).
2. Storage: Embeds the chunks using a local embedding model and stores them in PostgreSQL using the pgvector extension.
3. Retrieval: When you ask a question, it finds the most relevant chunks in the DB and feeds them to the LLM (Llama 3 or similar) to generate an answer.

Tech Stack
- Java & Spring Boot
- Ollama: For running the LLM locally (privacy-first)
- PostgreSQL + pgvector: Simple, efficient vector storage

Setup

Prerequisites
- Java
- Maven
- Ollama (running ollama serve)
- PostgreSQL with vector extension installed.

Database
CREATE DATABASE ragdb;
\c ragdb
CREATE EXTENSION vector;

Configuration
Edit src/main/resources/application.properties:
spring.datasource.username=your_user
spring.datasource.password=your_password

Run
mvn spring-boot:run

Then you can use the API endpoints to upload PDFs and ask questions.


