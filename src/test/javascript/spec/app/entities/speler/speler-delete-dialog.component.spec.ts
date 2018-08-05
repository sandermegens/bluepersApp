/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BluepersAppTestModule } from '../../../test.module';
import { SpelerDeleteDialogComponent } from 'app/entities/speler/speler-delete-dialog.component';
import { SpelerService } from 'app/entities/speler/speler.service';

describe('Component Tests', () => {
    describe('Speler Management Delete Component', () => {
        let comp: SpelerDeleteDialogComponent;
        let fixture: ComponentFixture<SpelerDeleteDialogComponent>;
        let service: SpelerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [SpelerDeleteDialogComponent]
            })
                .overrideTemplate(SpelerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpelerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpelerService);
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
