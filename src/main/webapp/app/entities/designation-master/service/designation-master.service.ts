import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDesignationMaster, NewDesignationMaster } from '../designation-master.model';

export type PartialUpdateDesignationMaster = Partial<IDesignationMaster> & Pick<IDesignationMaster, 'id'>;

export type EntityResponseType = HttpResponse<IDesignationMaster>;
export type EntityArrayResponseType = HttpResponse<IDesignationMaster[]>;

@Injectable({ providedIn: 'root' })
export class DesignationMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/designation-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(designationMaster: NewDesignationMaster): Observable<EntityResponseType> {
    return this.http.post<IDesignationMaster>(this.resourceUrl, designationMaster, { observe: 'response' });
  }

  update(designationMaster: IDesignationMaster): Observable<EntityResponseType> {
    return this.http.put<IDesignationMaster>(
      `${this.resourceUrl}/${this.getDesignationMasterIdentifier(designationMaster)}`,
      designationMaster,
      { observe: 'response' }
    );
  }

  partialUpdate(designationMaster: PartialUpdateDesignationMaster): Observable<EntityResponseType> {
    return this.http.patch<IDesignationMaster>(
      `${this.resourceUrl}/${this.getDesignationMasterIdentifier(designationMaster)}`,
      designationMaster,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDesignationMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDesignationMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDesignationMasterIdentifier(designationMaster: Pick<IDesignationMaster, 'id'>): number {
    return designationMaster.id;
  }

  compareDesignationMaster(o1: Pick<IDesignationMaster, 'id'> | null, o2: Pick<IDesignationMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getDesignationMasterIdentifier(o1) === this.getDesignationMasterIdentifier(o2) : o1 === o2;
  }

  addDesignationMasterToCollectionIfMissing<Type extends Pick<IDesignationMaster, 'id'>>(
    designationMasterCollection: Type[],
    ...designationMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const designationMasters: Type[] = designationMastersToCheck.filter(isPresent);
    if (designationMasters.length > 0) {
      const designationMasterCollectionIdentifiers = designationMasterCollection.map(
        designationMasterItem => this.getDesignationMasterIdentifier(designationMasterItem)!
      );
      const designationMastersToAdd = designationMasters.filter(designationMasterItem => {
        const designationMasterIdentifier = this.getDesignationMasterIdentifier(designationMasterItem);
        if (designationMasterCollectionIdentifiers.includes(designationMasterIdentifier)) {
          return false;
        }
        designationMasterCollectionIdentifiers.push(designationMasterIdentifier);
        return true;
      });
      return [...designationMastersToAdd, ...designationMasterCollection];
    }
    return designationMasterCollection;
  }
}
