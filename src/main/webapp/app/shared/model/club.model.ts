import { Moment } from 'moment';

export const enum CountryEnum {
    GUATEMALA = 'GUATEMALA',
    BELIZE = 'BELIZE',
    HONDURAS = 'HONDURAS'
}

export const enum CorEnum {
    GUATEMALA = 'GUATEMALA'
}

export const enum WeekdaysEnum {
    SUNDAY = 'SUNDAY',
    MONDAY = 'MONDAY',
    TUESDAY = 'TUESDAY',
    WEDNESDAY = 'WEDNESDAY',
    THURSDAY = 'THURSDAY',
    FRIDAY = 'FRIDAY',
    SATURDAY = 'SATURDAY'
}

export const enum ClubStatusEnum {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE',
    AWAITING_APPROVAL = 'AWAITING_APPROVAL'
}

export interface IClub {
    id?: number;
    name?: string;
    country?: CountryEnum;
    cor?: CorEnum;
    state?: string;
    city?: string;
    address?: string;
    rotaract_id?: number;
    creation_date?: Moment;
    certification_date?: Moment;
    meeting_day?: WeekdaysEnum;
    meeting_time?: string;
    y?: string;
    facebook_url?: string;
    instagram_url?: string;
    twitter_url?: string;
    rotary_club?: string;
    status?: ClubStatusEnum;
    comments?: string;
}

export class Club implements IClub {
    constructor(
        public id?: number,
        public name?: string,
        public country?: CountryEnum,
        public cor?: CorEnum,
        public state?: string,
        public city?: string,
        public address?: string,
        public rotaract_id?: number,
        public creation_date?: Moment,
        public certification_date?: Moment,
        public meeting_day?: WeekdaysEnum,
        public meeting_time?: string,
        public y?: string,
        public facebook_url?: string,
        public instagram_url?: string,
        public twitter_url?: string,
        public rotary_club?: string,
        public status?: ClubStatusEnum,
        public comments?: string
    ) {}
}
