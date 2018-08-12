import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from './team.service';
import { ICompetitie } from 'app/shared/model/competitie.model';
import { CompetitieService } from 'app/entities/competitie';
import { IWedstrijd } from 'app/shared/model/wedstrijd.model';
import { WedstrijdService } from 'app/entities/wedstrijd';

@Component({
    selector: 'jhi-team-update',
    templateUrl: './team-update.component.html'
})
export class TeamUpdateComponent implements OnInit {
    private _team: ITeam;
    isSaving: boolean;

    competities: ICompetitie[];

    wedstrijds: IWedstrijd[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private teamService: TeamService,
        private competitieService: CompetitieService,
        private wedstrijdService: WedstrijdService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ team }) => {
            this.team = team;
        });
        this.competitieService.query().subscribe(
            (res: HttpResponse<ICompetitie[]>) => {
                this.competities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.wedstrijdService.query().subscribe(
            (res: HttpResponse<IWedstrijd[]>) => {
                this.wedstrijds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.team.id !== undefined) {
            this.subscribeToSaveResponse(this.teamService.update(this.team));
        } else {
            this.subscribeToSaveResponse(this.teamService.create(this.team));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>) {
        result.subscribe((res: HttpResponse<ITeam>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCompetitieById(index: number, item: ICompetitie) {
        return item.id;
    }

    trackWedstrijdById(index: number, item: IWedstrijd) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get team() {
        return this._team;
    }

    set team(team: ITeam) {
        this._team = team;
    }
}
