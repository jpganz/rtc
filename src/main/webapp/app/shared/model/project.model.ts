import { Moment } from 'moment';
import { IClub } from 'app/shared/model//club.model';

export const enum AreaOfInteresEnum {
    FOOD = 'FOOD',
    WATER = 'WATER',
    HEALTH = 'HEALTH'
}

export const enum OrganizerCommitteeEnum {
    CLUB_SERVICE = 'CLUB_SERVICE',
    YOUTH = 'YOUTH',
    NONE = 'NONE'
}

export const enum ProjectStatusEnum {
    SUBMITTED = 'SUBMITTED',
    PENDING = 'PENDING',
    COMPLETED = 'COMPLETED',
    CANCELLED = 'CANCELLED'
}

export interface IProject {
    id?: number;
    project_name?: string;
    area_of_interes?: AreaOfInteresEnum;
    organizer_committee?: OrganizerCommitteeEnum;
    coordinator?: string;
    location?: string;
    date?: Moment;
    main_objective?: string;
    target_community?: string;
    direct_beneficiaries?: number;
    indirect_beneficiaries?: number;
    short_term_benefits?: string;
    mid_term_benefits?: string;
    long_term_benefits?: string;
    member_contributions?: number;
    fundraising?: number;
    donations?: number;
    donations_in_kind?: string;
    activity_description?: string;
    social_media_reference?: string;
    project_status?: ProjectStatusEnum;
    club?: IClub;
}

export class Project implements IProject {
    constructor(
        public id?: number,
        public project_name?: string,
        public area_of_interes?: AreaOfInteresEnum,
        public organizer_committee?: OrganizerCommitteeEnum,
        public coordinator?: string,
        public location?: string,
        public date?: Moment,
        public main_objective?: string,
        public target_community?: string,
        public direct_beneficiaries?: number,
        public indirect_beneficiaries?: number,
        public short_term_benefits?: string,
        public mid_term_benefits?: string,
        public long_term_benefits?: string,
        public member_contributions?: number,
        public fundraising?: number,
        public donations?: number,
        public donations_in_kind?: string,
        public activity_description?: string,
        public social_media_reference?: string,
        public project_status?: ProjectStatusEnum,
        public club?: IClub
    ) {}
}
