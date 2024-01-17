import express from "express";
import { createProxyMiddleware } from "http-proxy-middleware";
import dotenv from "dotenv";
import { fileURLToPath } from "url";
import { join, dirname } from "path";

dotenv.config();

const app = express();

// Configuration
const { PORT } = process.env;
const { HOST } = process.env;
const { API_BASE_URL } = process.env;

// Proxy
app.use(
  "/api",
  createProxyMiddleware({
    target: API_BASE_URL,
    changeOrigin: true,
  })
);

const staticPath = join(dirname(fileURLToPath(import.meta.url)), "dist");

app.use(express.static(staticPath))

app.listen(PORT, HOST, () => {
  console.info(`Starting Proxy at ${HOST}:${PORT}`);
});

app.get("*", async (req, res) => {
  res.sendFile(join(staticPath, "index.html"))
});
