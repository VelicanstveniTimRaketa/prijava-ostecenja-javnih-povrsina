import { hash as bhash } from "bcryptjs";
import { PasswordHash } from "./types";

export async function hash(password: string): Promise<PasswordHash> {
  return new Promise(res => bhash(password, 10).then(res));
}
