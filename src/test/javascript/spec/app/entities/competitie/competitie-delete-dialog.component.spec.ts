/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BluepersAppTestModule } from '../../../test.module';
import { CompetitieDeleteDialogComponent } from 'app/entities/competitie/competitie-delete-dialog.component';
import { CompetitieService } from 'app/entities/competitie/competitie.service';

describe('Component Tests', () => {
    describe('Competitie Management Delete Component', () => {
        let comp: CompetitieDeleteDialogComponent;
        let fixture: ComponentFixture<CompetitieDeleteDialogComponent>;
        let service: CompetitieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [CompetitieDeleteDialogComponent]
            })
                .overrideTemplate(CompetitieDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompetitieDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompetitieService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
