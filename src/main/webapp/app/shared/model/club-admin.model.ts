import { IUser } from 'app/core/user/user.model';
import { IClub } from 'app/shared/model//club.model';

export interface IClub_admin {
    id?: number;
    user?: IUser;
    club?: IClub;
}

export class Club_admin implements IClub_admin {
    constructor(public id?: number, public user?: IUser, public club?: IClub) {}
}
