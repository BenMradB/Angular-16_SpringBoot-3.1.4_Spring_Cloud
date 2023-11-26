import { Role } from './Roles.models';

export interface User {
  user_id: number;
  username: string;
  password: string;
  isEnabled: Boolean;
  roles: Role[];
}
