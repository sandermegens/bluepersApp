import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWedstrijd } from 'app/shared/model/wedstrijd.model';
import { WedstrijdService } from './wedstrijd.service';

@Component({
    selector: 'jhi-wedstrijd-delete-dialog',
    templateUrl: './wedstrijd-delete-dialog.component.html'
})
export class WedstrijdDeleteDialogComponent {
    wedstrijd: IWedstrijd;

    constructor(private wedstrijdService: WedstrijdService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wedstrijdService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wedstrijdListModification',
                content: 'Deleted an wedstrijd'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wedstrijd-delete-popup',
    template: ''
})
export class WedstrijdDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wedstrijd }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WedstrijdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.wedstrijd = wedstrijd;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
