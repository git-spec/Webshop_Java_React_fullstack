import type { IUserAuth } from "@/interface/IUserAuth";


export type UserContextType = {
  user: IUserAuth | null | undefined;
  updateUser: (user: IUserAuth | null | undefined) => void;
};