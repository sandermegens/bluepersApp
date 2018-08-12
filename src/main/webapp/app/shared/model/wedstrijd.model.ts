import { Moment } from 'moment';
import { ITeam } from 'app/shared/model//team.model';

export interface IWedstrijd {
    id?: number;
    datum?: Moment;
    tijd?: Moment;
    plaats?: string;
    competitieId?: number;
    teams?: ITeam[];
}

export class Wedstrijd implements IWedstrijd {
    constructor(
        public id?: number,
        public datum?: Moment,
        public tijd?: Moment,
        public plaats?: string,
        public competitieId?: number,
        public teams?: ITeam[]
    ) {}
}
