import { Moment } from 'moment';
import { IClub } from 'app/shared/model//club.model';
import { IUser } from 'app/core/user//user.model';

export const enum MemberStatusEnum {
    ACTIVE = 'ACTIVE',
    ABSENT_ON_LEAVE = 'ABSENT_ON_LEAVE',
    INACTIVE = 'INACTIVE'
}

export const enum GenderEnum {
    MALE = 'MALE',
    FEMALE = 'FEMALE'
}

export const enum PositionEnum {
    PRESIDENT = 'PRESIDENT',
    VICEPRESIDENT = 'VICEPRESIDENT',
    TREASUR = 'TREASUR',
    SECRETARY = 'SECRETARY',
    PASTPRESIDENT = 'PASTPRESIDENT',
    NONE = 'NONE'
}

export const enum CommitteeEnum {
    INTERNATIONAL_SERVICE = 'INTERNATIONAL_SERVICE',
    CLUB_SERVICE = 'CLUB_SERVICE',
    VOCATIONAL_SERVICE = 'VOCATIONAL_SERVICE',
    YOUTH_SERVICE = 'YOUTH_SERVICE',
    PUBLIC_RELATIONS = 'PUBLIC_RELATIONS',
    GREEN_ROTARACT = 'GREEN_ROTARACT',
    NONE = 'NONE'
}

export const enum ProfessionalAreaEnum {
    LEGAL = 'LEGAL',
    FINANCE = 'FINANCE',
    ENGINEERING = 'ENGINEERING',
    OTHER = 'OTHER'
}

export interface IMember {
    id?: number;
    name?: string;
    last_name?: string;
    status?: MemberStatusEnum;
    gender?: GenderEnum;
    birthdate?: Moment;
    position?: PositionEnum;
    committee?: CommitteeEnum;
    professional_area?: ProfessionalAreaEnum;
    starting_membership?: Moment;
    ending_membership?: Moment;
    email?: string;
    phone?: string;
    club?: IClub;
    user?: IUser;
}

export class Member implements IMember {
    constructor(
        public id?: number,
        public name?: string,
        public last_name?: string,
        public status?: MemberStatusEnum,
        public gender?: GenderEnum,
        public birthdate?: Moment,
        public position?: PositionEnum,
        public committee?: CommitteeEnum,
        public professional_area?: ProfessionalAreaEnum,
        public starting_membership?: Moment,
        public ending_membership?: Moment,
        public email?: string,
        public phone?: string,
        public club?: IClub,
        public user?: IUser
    ) {}
}
