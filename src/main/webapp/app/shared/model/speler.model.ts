import { Moment } from 'moment';

export const enum Positie {
    KEEPER = 'KEEPER',
    LINKSBACK = 'LINKSBACK',
    CENTRALEVERDEDIGER = 'CENTRALEVERDEDIGER',
    RECHTSBACK = 'RECHTSBACK',
    RECHTERMIDDENVELDER = 'RECHTERMIDDENVELDER',
    LINKERMIDDENVELDER = 'LINKERMIDDENVELDER',
    CENTRALEMIDDENVELDER = 'CENTRALEMIDDENVELDER',
    LINKSBUITEN = 'LINKSBUITEN',
    RECHTSBUITEN = 'RECHTSBUITEN',
    SPITS = 'SPITS'
}

export interface ISpeler {
    id?: number;
    voornaam?: string;
    tussenvoegsel?: string;
    achternaam?: string;
    rugnummer?: number;
    positie?: Positie;
    geboorteDatum?: Moment;
    debuut?: Moment;
    bijzonderheden?: string;
    teamId?: number;
}

export class Speler implements ISpeler {
    constructor(
        public id?: number,
        public voornaam?: string,
        public tussenvoegsel?: string,
        public achternaam?: string,
        public rugnummer?: number,
        public positie?: Positie,
        public geboorteDatum?: Moment,
        public debuut?: Moment,
        public bijzonderheden?: string,
        public teamId?: number
    ) {}
}
