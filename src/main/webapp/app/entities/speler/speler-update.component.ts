import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISpeler } from 'app/shared/model/speler.model';
import { SpelerService } from './speler.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';

@Component({
    selector: 'jhi-speler-update',
    templateUrl: './speler-update.component.html'
})
export class SpelerUpdateComponent implements OnInit {
    private _speler: ISpeler;
    isSaving: boolean;

    teams: ITeam[];
    geboorteDatumDp: any;
    debuutDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private spelerService: SpelerService,
        private teamService: TeamService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ speler }) => {
            this.speler = speler;
        });
        this.teamService.query().subscribe(
            (res: HttpResponse<ITeam[]>) => {
                this.teams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.speler.id !== undefined) {
            this.subscribeToSaveResponse(this.spelerService.update(this.speler));
        } else {
            this.subscribeToSaveResponse(this.spelerService.create(this.speler));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISpeler>>) {
        result.subscribe((res: HttpResponse<ISpeler>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }
    get speler() {
        return this._speler;
    }

    set speler(speler: ISpeler) {
        this._speler = speler;
    }
}
