import { useEffect, useState } from "react";
import { User } from "../utils/types";
import { getAllUsers } from "../utils/fetch";

export function useUsers() {
  const [users, setUsers] = useState<User[]>();
  useEffect(() => {
    getAllUsers().then(res => setUsers(res.data));
  }, []);
  return users;
}