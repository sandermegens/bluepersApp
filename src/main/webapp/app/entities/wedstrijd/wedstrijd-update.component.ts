import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IWedstrijd } from 'app/shared/model/wedstrijd.model';
import { WedstrijdService } from './wedstrijd.service';
import { ICompetitie } from 'app/shared/model/competitie.model';
import { CompetitieService } from 'app/entities/competitie';

@Component({
    selector: 'jhi-wedstrijd-update',
    templateUrl: './wedstrijd-update.component.html'
})
export class WedstrijdUpdateComponent implements OnInit {
    private _wedstrijd: IWedstrijd;
    isSaving: boolean;

    competities: ICompetitie[];
    datumDp: any;
    tijdDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private wedstrijdService: WedstrijdService,
        private competitieService: CompetitieService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wedstrijd }) => {
            this.wedstrijd = wedstrijd;
        });
        this.competitieService.query().subscribe(
            (res: HttpResponse<ICompetitie[]>) => {
                this.competities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wedstrijd.id !== undefined) {
            this.subscribeToSaveResponse(this.wedstrijdService.update(this.wedstrijd));
        } else {
            this.subscribeToSaveResponse(this.wedstrijdService.create(this.wedstrijd));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWedstrijd>>) {
        result.subscribe((res: HttpResponse<IWedstrijd>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get wedstrijd() {
        return this._wedstrijd;
    }

    set wedstrijd(wedstrijd: IWedstrijd) {
        this._wedstrijd = wedstrijd;
    }
}