import { Fetcher, getUserFromToken } from "./fetch";
import { CurrentUser, User } from "./types";

export function getUser(user: User, token: string, refreshToken: string): CurrentUser {
  const u = { ...user, token, refreshToken };
  saveUser(u);
  return u;
}

export async function getSavedUser() {
  const token = localStorage.getItem("token");
  const refreshToken = localStorage.getItem("refreshToken");
  const timeSaved = localStorage.getItem("timeSaved");

  if (!token || !refreshToken || !timeSaved) {
    clearSavedUser();
    return;
  }

  const timeElapsed = (new Date().getTime() - new Date(timeSaved).getTime()) / 1000 / 60; // minutes
  if (timeElapsed > 15) {
    clearSavedUser();
    return;
  }

  Fetcher.setToken(token);
  const r = await getUserFromToken();
  if (r.success && r.data) {
    const user = { ...r.data, token: token, refreshToken: refreshToken };
    saveUser(user);
    return user;
  }
  clearSavedUser();
}

export function saveUser(user: CurrentUser) {
  localStorage.setItem("token", user.token);
  localStorage.setItem("refreshToken", user.refreshToken);
  localStorage.setItem("timeSaved", new Date().toISOString());
  Fetcher.setToken(user.token);
}

export function clearSavedUser() {
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("timeSaved");
  Fetcher.setToken(undefined);
}
