import type { IUser } from "@/interface/IUser";


export type UserContextType = {
  user: IUser | null | undefined;
  updateUser: (user: IUser | null | undefined) => void;
};